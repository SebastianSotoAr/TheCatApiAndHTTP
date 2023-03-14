package service.apiconnection;

import com.google.gson.Gson;
import com.squareup.okhttp.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;


public class GatoService
{

    public static void verGatos() throws IOException
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/images/search")
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String json = response.body().string();
        json = json.substring(1, json.length() - 1);
        //Cortar corchetes
        Gson gson = new Gson();
        Gato gato = gson.fromJson(json, Gato.class);
        //Redimencionar imagen
        Image image = ImageIO.read(new URL(gato.getUrl()));
        ImageIcon imageIcon = new ImageIcon(image);

        if(imageIcon.getIconWidth() > 200)
        {
            image.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
        }

        String menu = "Opciones:\n\t1. Ver Otro Gato\n\t2. Favorito\n\t3. Volver";
        String[] botones = {"Ver Otro Gato", "Favorito", "Volver"};
        String opcion = (String) JOptionPane.showInputDialog
                (
                        null,
                        menu,
                        "Gato",
                        JOptionPane.INFORMATION_MESSAGE,
                        imageIcon,
                        botones,
                        botones[0]
                );
        int opcion_menu = -1;

        for (int i = 0; i < botones.length; i++)
        {
            if (opcion.equals(botones[i])) {opcion_menu = i;}
        }

        switch (opcion_menu)
        {
            case 0 -> {verGatos();}
            case 1 -> {favoritoGato(gato);}
        }
    }

    public static void favoritoGato(Gato gato)
    {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(
                    mediaType,
                    "{\r\n    \"image_id\":\""+gato.id+"\"\r\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites?x-api-key=")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", (new ApiId()).getID())
                    .build();
        try {Response response = client.newCall(request).execute();}
        catch (IOException e) {System.out.println(e.getMessage());}
    }

    public static void verFavoritos()
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites")
                .get()
                .addHeader("x-api-key", (new ApiId()).getID())
                .build();
        try {
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            Gson gson = new Gson();
            GatosFav[] gatosArray = gson.fromJson(json, GatosFav[].class);
            if(gatosArray.length > 0)
            {
                int min = 1;
                int max = gatosArray.length;
                int aleatorio = (int) (Math.random()*(max));
                GatosFav gatosFav = gatosArray[aleatorio];
                Image image = ImageIO.read(new URL(gatosFav.getImage().getUrl()));
                ImageIcon imageIcon = new ImageIcon(image);

                if(imageIcon.getIconWidth() > 200)
                {
                    image.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                    imageIcon = new ImageIcon(image);
                }

                String menu = "Opciones:\n\t1. Ver Otro Gato\n\t2. Eliminar Favorito\n\t3. Volver";
                String[] botones = {"Ver Otro Gato", "Eliminar Favorito", "Volver"};
                String opcion = (String) JOptionPane.showInputDialog
                        (
                                null,
                                menu,
                                "Gato",
                                JOptionPane.INFORMATION_MESSAGE,
                                imageIcon,
                                botones,
                                botones[0]
                        );
                int opcion_menu = -1;

                for (int i = 0; i < botones.length; i++)
                {
                    if (opcion.equals(botones[i])) {opcion_menu = i;}
                }

                switch (opcion_menu)
                {
                    case 0 -> {verFavoritos();}
                    case 1 -> {borrarFavorito(gatosFav);}
                }
            }
        }
        catch (IOException e) {System.out.println(e.getMessage());}
    }

    private static void borrarFavorito(GatosFav gatosFav)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/favourites/"+gatosFav.getId())
                .delete()
                .addHeader("Content-Type", "application/json")
                .addHeader("x-api-key", (new ApiId()).getID())
                .build();
        try {Response response = client.newCall(request).execute();}
        catch (IOException e) {System.out.println(e.getMessage());}
    }
}
