package br.com.alura.DaysOfCodeSpring;

import java.io.PrintWriter;
import java.util.List;

public class HTMLGenerator {
    private PrintWriter writer;

    public HTMLGenerator(PrintWriter writer) {
        this.writer = writer;
    }

    private String head =
            """
                <html>
                    <head>
                        <meta charset="utf-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
                        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous"s>					
                        <link href="./main.css" rel="stylesheet">
                    </head>      
            """;

    private String card =
            """       
                    <div class="card text-white bg-dark mb-3" style="max-width: 12rem;">
                        <h4 class="card-header">%s</h4>
                        <div class="card-body">
                            <img class="card-img" src="%s" alt="%ss">
                            <p class="card-text mt-2">Nota: %s - Ano: %s</p>
                        </div>
                    </div>                             
            """;

    public void generate(List<ApiController.Filme> filmes) {
        writer.println(head);

        writer.println("""
                <body>
                    <h1 class="title">IMDb</h1>
                    <div class="content">
                """);

        filmes.forEach(filme -> {
            writer.println(String.format(card, filme.title(), filme.image(), filme.title(), filme.imDbRating(), filme.year()));
        });

        writer.println(
                """
                    </div>
                    </body>
                </html>
                """);
    }
}
