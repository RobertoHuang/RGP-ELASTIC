/**
 * FileName: IndexRequestTest
 * Author:   HuangTaiHong
 * Date:     2020/4/21
 * Description: index request test.
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package roberto.growth.process.elastic.search;

import org.assertj.core.util.Maps;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

import java.io.IOException;

/**
 * 〈index request test.〉
 *
 * @author HuangTaiHong
 * @since 2020/4/21
 */
public class IndexRequestTest extends BaseTest {
    @Test
    public void testBuildIndexRequest() throws IOException {
        // 键值对方式构建文档
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE);
        indexRequest.source("field_name", "field_value");
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        // JSON方式构建文档
        indexRequest = indexRequest.source("{\n" + "  \"field_name2\": \"field_value2\"\n" + "}", XContentType.JSON);
        restHighLevelClient.index(indexRequest);

        // Map方式构建文档
        indexRequest.source(Maps.newHashMap("field_name3", "field_value3"));
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        // 基于XContentBuilder构建
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
        xContentBuilder.startObject();
        xContentBuilder.field("field_name4", "field_value4");
        xContentBuilder.endObject();
        indexRequest.source(xContentBuilder);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }

    @Test
    public void test() throws IOException, InterruptedException {
//        UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, "qtjSnHEBTbGicjqOVYS888");
//        updateRequest.doc(Maps.newHashMap("name", "roberto"));
//        updateRequest.upsert(Maps.newHashMap("name2", "roberto2"));
//        restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);

        BulkProcessor bulkProcessor = BulkProcessor.builder(((bulkRequest, bulkResponseActionListener) -> {
            restHighLevelClient.bulkAsync(bulkRequest, RequestOptions.DEFAULT, bulkResponseActionListener);
        }), new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                System.out.println("!231231");
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                System.out.println("123123");
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {

            }
        }).setFlushInterval(TimeValue.timeValueSeconds(10L)).build();
        for (int i = 0; i < 100; i++) {
            IndexRequest indexRequest = new IndexRequest(INDEX, TYPE);
            indexRequest.source("field_name", "field_value");
            bulkProcessor.add(indexRequest);
        }

        Thread.currentThread().join();
    }
}
