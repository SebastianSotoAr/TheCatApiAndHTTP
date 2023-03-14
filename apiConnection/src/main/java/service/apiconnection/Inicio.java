package service.apiconnection;

import javax.swing.JOptionPane;
import java.io.IOException;

public class Inicio
{
    public static void main(String[] args)
    {
        int opcion_menu = -1;
        String[] botones = {"Ver Gatos", "Ver Favoritos", "Salir"};
        do
        {
            String opcion = (String) JOptionPane.showInputDialog
                    (
                            null,
                            "Gatitos Java",
                            "Menu principal",
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            botones,
                            botones[0]
                    );

            for (int i = 0; i < botones.length; i++)
            {
                if (opcion.equals(botones[i])) {opcion_menu = i;}
            }

            switch (opcion_menu)
            {
                case 0 ->
                {
                    try {GatoService.verGatos();}
                    catch (IOException e) {System.out.println(e.getMessage());}
                }
                case 1 -> {GatoService.verFavoritos();}
                default -> {break;}
            }
        }
        while(opcion_menu != 1);
    }
}
