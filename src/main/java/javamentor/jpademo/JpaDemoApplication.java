package javamentor.jpademo;

import javamentor.jpademo.dao.GenreRepository;
import javamentor.jpademo.model.Book;
import javamentor.jpademo.services.DemoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class JpaDemoApplication implements CommandLineRunner {

    @Resource
    private GenreRepository genreRepository;

    @Resource
    private DemoService demoService;

    public static void main(String[] args) {
        SpringApplication.run(JpaDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // System.out.println(genreRepository.count());
        // System.out.println(demoService.equalsTest());

        // demoService.listTest();

        demoService.createAuthorWithoutTransaction("Петр", "Петр", "Петров");
        // demoService.fetchStrategyDemo();
        // System.out.println(demoService.hqlDemo());
        // System.out.println(demoService.jpqlDemo());
        // System.out.println(demoService.criteriaApiDemo());
        // System.out.println(demoService.nativeSqlDemo());
        // System.out.println(demoService.namedQueryDemo());
        // demoService.entityGraphDemo();

        // demoService.createAuthor("Петр", "Петр", "Петров");

        System.out.println("=== OK");
    }
}
