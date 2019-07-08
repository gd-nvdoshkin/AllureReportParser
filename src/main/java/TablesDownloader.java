import client.BambooClient;
import feign.Feign;
import feign.Logger;
import feign.Response;
import feign.form.FormEncoder;
import feign.slf4j.Slf4jLogger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import util.ConfigReader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TablesDownloader {
    static List<String> jobs = ConfigReader.getPropertyAsList("bamboo.jobs");


    public static void main(String[] args) throws IOException {
        String userName = ConfigReader.getProperty("bamboo.username");
        String password = ConfigReader.getProperty("bamboo.password");

        BambooClient bambooClient = Feign.builder()
                .encoder(new FormEncoder())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .target(BambooClient.class, ConfigReader.getProperty("bamboo.url"));
        Response response = bambooClient.getJSessionId(userName, password);
        String jSessionId = response.headers().get("set-cookie").toString().split(";")[5].replaceAll("Secure, ", "");


        int index = 1;
        for (String job : jobs) {
            StringBuilder result = new StringBuilder();
            if (!job.isEmpty()) {
                String subJobs = bambooClient.getSubJobs(jSessionId, job);
                Document document = Jsoup.parse(subJobs);
                List<String> subJobIds = document.select("li[data-job-key]").stream().map(element -> element.attr("data-job-key").split("-")[2]).collect(Collectors.toList());


                for (String id : subJobIds) {
                    result.append(bambooClient.getTableText(jSessionId, "/browse/" + job + "/artifact/" + id + ConfigReader.getProperty("table.postfix")));
                }
            }

            FileWriter fw = new FileWriter("tables/" + index + ".txt");
            fw.write(result.toString());
            fw.flush();
            fw.close();
            index++;
        }
    }
}
