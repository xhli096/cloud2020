package com.xinghaol.kafka.admin;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.AlterConfigsResult;
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.clients.admin.CreatePartitionsResult;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewPartitions;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.config.ConfigResource.Type;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author: lixinghao
 * @date: 2020/9/20 9:49 下午
 * @Description: 演示如何获取一个AdminClient
 */
public class AdminClientSample {
    private static final String TOPIC_NAME = "xinghaol-java-topic";

    public static void main(String[] args) throws Exception {
        AdminClient adminClient = AdminClientSample.getAdminClient();
        System.out.println(adminClient);
        System.out.println("====================分界线====================");
        createTopics(adminClient);
        System.out.println("====================分界线====================");
        getTopicLists(adminClient);
        System.out.println("====================分界线====================");
        deleteTopic(adminClient);
        System.out.println("====================分界线====================");
        getTopicLists(adminClient);
        System.out.println("====================分界线====================");
        createTopics(adminClient);
        describeTopics(adminClient);
        System.out.println("====================分界线====================");
        describeTopicConfig(adminClient);
        System.out.println("====================分界线====================");
        alterTopicConfig(adminClient);
        describeTopicConfig(adminClient);
        System.out.println("====================分界线====================");
        incrementPartitions(adminClient, 2);
        describeTopics(adminClient);
    }

    /**
     * 获得一个AdminClient
     *
     * @return
     */
    public static AdminClient getAdminClient() {
        Properties properties = new Properties();
        properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.218.128:9092");

        AdminClient adminClient = AdminClient.create(properties);

        return adminClient;
    }

    /**
     * 创建Topics
     *
     * @param adminClient
     */
    public static void createTopics(AdminClient adminClient) {
        // 副本集变量为short类型
        NewTopic newTopic = new NewTopic(TOPIC_NAME, 1, (short) 1);
        CreateTopicsResult topics = adminClient.createTopics(Arrays.asList(newTopic));
        System.out.println(topics);
    }

    /**
     * 查看topic list
     *
     * @param adminClient
     */
    public static void getTopicLists(AdminClient adminClient) throws Exception {
        // 是否查看internal
        ListTopicsOptions options = new ListTopicsOptions();
        options.listInternal(true);

//        ListTopicsResult listTopicsResult = adminClient.listTopics();
        ListTopicsResult listTopicsResult = adminClient.listTopics(options);
        Set<String> topicNames = listTopicsResult.names().get();
        topicNames.stream().forEach(System.out::println);

        Collection<TopicListing> topicListings = listTopicsResult.listings().get();
        topicListings.stream().forEach(topicListing -> {
            System.out.println(topicListing);
        });
    }

    /**
     * 删除topic
     *
     * @param adminClient
     */
    public static void deleteTopic(AdminClient adminClient) throws Exception {
        DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(Arrays.asList(TOPIC_NAME));
        System.out.println(JSON.toJSONString(deleteTopicsResult));
    }

    /**
     * 查看topic的描述，用于监控kafka
     *
     * @param adminClient
     */
    public static void describeTopics(AdminClient adminClient) throws Exception {
        DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(Arrays.asList(TOPIC_NAME));
        Map<String, TopicDescription> stringTopicDescriptionMap = describeTopicsResult.all().get();
        for (Map.Entry<String, TopicDescription> entry : stringTopicDescriptionMap.entrySet()) {
            String name = entry.getKey();
            TopicDescription value = entry.getValue();
            System.out.println("name:" + name + ", desc:" + value);
        }
    }

    /**
     * 查看topic的config描述，用于监控kafka
     *
     * @param adminClient
     */
    public static void describeTopicConfig(AdminClient adminClient) throws Exception {
        // TODO 预留一个位置，在集群时会进行讲解
        // ConfigResource configResource = new ConfigResource(Type.BROKER, TOPIC_NAME);
        ConfigResource configResource = new ConfigResource(Type.TOPIC, TOPIC_NAME);
        DescribeConfigsResult describeConfigsResult = adminClient.describeConfigs(Arrays.asList(configResource));
        Map<ConfigResource, Config> configResourceConfigMap = describeConfigsResult.all().get();
        configResourceConfigMap.entrySet().stream().forEach(entry -> {
            System.out.println("configResource:" + entry.getKey() + ", config:" + entry.getValue());
        });
    }

    /**
     * 修改topic的config描述
     *
     * @param adminClient
     * @throws Exception
     */
    public static void alterTopicConfig(AdminClient adminClient) throws Exception {
        Map<ConfigResource, Config> param = new HashMap<>();
        ConfigResource configResource = new ConfigResource(Type.TOPIC, TOPIC_NAME);
        Config config = new Config(Arrays.asList(new ConfigEntry("preallocate", "true")));
        param.put(configResource, config);

        AlterConfigsResult alterConfigsResult = adminClient.alterConfigs(param);
        alterConfigsResult.all().get();

        /*
         */
/*
            从 2.3以上的版本新修改的API
         *//*

        Map<ConfigResource,Collection<AlterConfigOp>> configMaps = new HashMap<>();
        // 组织两个参数
        ConfigResource configResource = new ConfigResource(ConfigResource.Type.TOPIC, TOPIC_NAME);
        AlterConfigOp alterConfigOp =
                new AlterConfigOp(new ConfigEntry("preallocate","false"),AlterConfigOp.OpType.SET);
        configMaps.put(configResource,Arrays.asList(alterConfigOp));

        AlterConfigsResult alterConfigsResult = adminClient.incrementalAlterConfigs(configMaps);
        alterConfigsResult.all().get();
*/
    }

    /**
     * 增加partition的数量，kafka中不支持减少和删除partition的操作
     *
     * @param adminClient
     * @param partitions
     */
    public static void incrementPartitions(AdminClient adminClient, int partitions) throws ExecutionException, InterruptedException {
        Map<String, NewPartitions> param = new HashMap<>();
        NewPartitions newPartitions = NewPartitions.increaseTo(partitions);
        param.put(TOPIC_NAME, newPartitions);

        CreatePartitionsResult partitionsResult = adminClient.createPartitions(param);
        partitionsResult.all().get();
    }
}
