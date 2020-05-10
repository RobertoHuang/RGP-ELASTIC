/**
 * FileName: BaseTest
 * Author:   HuangTaiHong
 * Date:     2020/4/21
 * Description: 测试基类.
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package roberto.growth.process.elastic.search;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.assertj.core.util.Lists;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 〈测试基类.〉
 *
 * @author HuangTaiHong
 * @since 2020/4/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTest {
    public static final String TYPE = "_doc";
    public static final String INDEX = "test_index";

    public static RestHighLevelClient restHighLevelClient;

    @BeforeClass
    public static void initRestHiLevelClient() {
        List<HttpHost> httpHostList = Lists.newArrayList();
        httpHostList.add(new HttpHost("118.24.206.22", 9200, "http"));
        final RestClientBuilder builder = RestClient.builder(httpHostList.toArray(new HttpHost[0]));

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("superadmin", "superadmin"));
        builder.setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        restHighLevelClient = new RestHighLevelClient(builder);
    }
}
