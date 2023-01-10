package br.com.alura.DaysOfCodeSpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/imdb")
public class ApiController {

    @Autowired
    private imdbApiClient apiClient;

    @Value("${imdb.apiKey}")
    private String apiKey;

    private List<Filme> favoritos = new ArrayList<>();

    @GetMapping
    public ModelAndView getFilmesHTML() throws Exception {
        List<Filme> filmes = apiClient.getBody(apiKey);

        PrintWriter writer = new PrintWriter("src/main/resources/static/content.html");
        new HTMLGenerator(writer).generate(filmes);
        writer.close();

        return new ModelAndView("content.html");
    }

    @GetMapping("/api")
    public List<Filme> getFilmes(@RequestParam(required = false) String titulo) throws Exception {
        if (titulo != null) {
            return apiClient.getBody(apiKey).stream().filter(f -> titulo.toLowerCase().contains(f.title.toLowerCase())).toList();
        }
        return apiClient.getBody(apiKey);
    }

    @PostMapping("/favoritos/{id}")
    public ResponseEntity addFilmeFavorito(@PathVariable String id) throws Exception {
        favoritos.addAll(apiClient.getBody(apiKey).stream().filter(f -> id.equals(f.id)).toList());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/favoritos")
    public List<Filme> getFilmesFavoritos() {
        return favoritos;
    }

    @GetMapping("/favoritos/{id}")
    public List<Filme> getFilmeFavorito(@PathVariable String id) {
        List<Filme> filme = favoritos.stream().filter(f -> id.equals(f.id)).toList();
        return filme;
    }

    @DeleteMapping("/favoritos/{id}")
    public List<Filme> deleteFilmeFavoritos(@PathVariable String id) {
        favoritos.removeAll(favoritos.stream().filter(f -> id.equals(f.id)).toList());
        return favoritos;
    }

    record Filme(String id, Long rank, String title, String fullTitle, String year, String image, String crew, String imDbRating, Long imDbRatingCount){}

}
