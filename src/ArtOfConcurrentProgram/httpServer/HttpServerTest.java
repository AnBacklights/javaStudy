package ArtOfConcurrentProgram.httpServer;

public class HttpServerTest {
    public static void main(String[] args) throws Exception {
        SimpleHttpServer.setBasePath("G:\\图片");
        SimpleHttpServer.start();
    }
}
