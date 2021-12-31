package pl.pp.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@SpringBootApplication
public class PpProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PpProjectApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getDefault());
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(dateFormat);
        return mapper;
    }

}
