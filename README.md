# http-client-utli
httpClient 工具类 

现在微服务这么火，在日常开发中我们经常需要调用http接口，虽然java自身提供了net相关工具包，但是其灵活性和功能总是不如人意，
于是有了apache httpclient 类库 ，大部分时候我们都是需要用的时候，百度一下，复制黏贴就使用，也不管它什么性能优化使用连接池等等，能调通就行。
在项目中对于这个工具类库也许没有进行很好的封装。在哪里使用就写在哪里，很多地方用到，就在多个地方写。反正是复制粘贴，很方便，但是这样就会导致项目中大量代码冗余。
所以这里对httpcient的常用操作封装成一个工具类


#### 使用方法

```
/**
 * @author Yang WenJie
 * @date 2018/2/10 下午9:26
 */
public class HttpClientDemo {

    @Test
    public void get() throws IOException{
        HttpRequestConfig config = HttpRequestConfig.create().url("http://localhost:8090/peoples/");
        HttpRequestResult result = HttpClientUtils.get(config);
        System.out.println(result.getResponseText());
    }

    @Test
    public void post() throws IOException {
        HttpRequestConfig config = HttpRequestConfig.create().url("http://localhost:8090/peoples/")
                                    .addParam("id",2)
                                    .addParam("name","yangwenjie2")
                                    .addParam("age",19);
        HttpRequestResult result = HttpClientUtils.post(config);
        System.out.println(result.getResponseText());

    }

    @Test
    public void postJson() throws IOException {
        HttpRequestConfig config = HttpRequestConfig.create().url("http://localhost:8090/peoples/body")
                .httpEntityType(HttpEntityType.ENTITY_STRING)
                .json("{\"name\",\"杨文杰\"}");
        HttpRequestResult result = HttpClientUtils.post(config);
        System.out.println(result.getResponseText());
    }

    @Test
    public void postByte() throws IOException {
        HttpRequestConfig config = HttpRequestConfig.create().url("http://localhost:8090/peoples/body")
                .httpEntityType(HttpEntityType.ENTITY_BYTES)
                .bytes("{\"name\",\"杨文杰\"}".getBytes("UTF-8"));
        HttpRequestResult result = HttpClientUtils.post(config);
        System.out.println(result.getResponseText());
    }

    @Test
    public void postInputStream() throws IOException {
        HttpRequestConfig config = HttpRequestConfig.create().url("http://localhost:8090/peoples/inputStream")
                .httpEntityType(HttpEntityType.ENTITY_STRING)
                .json("{\"name\",\"杨文杰\"}");
        HttpRequestResult result = HttpClientUtils.post(config);
        System.out.println(result);
    }

    @Test
    public void down() throws IOException {
        String imgUrl = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_fe6da1ec.png"; //百度logo
        File file = new File("/Users/yangwenjie/test/baidu.png");
        HttpClientUtils.down(HttpRequestConfig.create().url(imgUrl).out(new FileOutputStream(file)));
        if (file.exists()) {
            System.out.println("图片下载成功了！存放在：" + file.getPath());
        }

    }


}
```