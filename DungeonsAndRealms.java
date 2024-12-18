package primerTrimestre;

import java.util.Scanner;

public class DungeonsAndRealms {

    // Variables estáticas
    public static final String RESET = "\033[0m\033[49m"; // Resetea estilo y fondo
    public static final String ROJO = "\033[31m"; // Texto en rojo
    public static final String VERDE = "\033[32m"; // Texto en verde
    public static final String AZUL = "\033[34m"; // Texto en azul
    static final String GRIS = "\u001B[37m"; // Texto en gris claro

    static Scanner input = new Scanner(System.in); // Inicializa el escáner para entrada del usuario
    static String nombreJugador = (""); // Nombre del jugador
    static int maximoBarraJugador = 100; // Vida máxima del jugador
    static int vidaJugador = maximoBarraJugador; // Vida actual del jugador
    static int maximoBarraEnemigo = 100; // Vida máxima del enemigo
    static int vidaEnemigo = maximoBarraEnemigo; // Vida actual del enemigo
    static int nivelJugador = 1; // Nivel inicial del jugador
    static int nivelEnemigo = 1; // Nivel inicial del enemigo
    static String[][] mapa = inicializacionMapa(); // Inicializa el mapa como matriz irregular
    static String[][] mapaCopia; // Copia del mapa para verificar enemigos derrotados
    static { mezclaMapa(mapa, mapaCopia); } // Mezcla ambos mapas para mantener consistencia
    static String Localizacion = "Inicio"; // Localización inicial del jugador
    static int filaActual = 0; // Fila actual en el mapa
    static int columnaActual = 0; // Columna actual en el mapa
    static int iteracionLocalizacionActual = 0; // Controla la iteración de la localización
    static int iteracionSalidaMapa = 0; // Controla la iteración para mostrar el mapa
    static int dañoEnemigo = 15; // Daño infligido por el enemigo
    static int dañoJugador = 35; // Daño infligido por el jugador
    static int barraNivelProgresado = 0; // Progreso de la barra de nivel del jugador
    static int defensa = 60; // Defensa del jugador
    static boolean ataqueExitosoEnemigo; // Indica si el ataque del enemigo fue exitoso
    static boolean ataqueCriticoEnemigo; // Indica si el ataque del enemigo fue crítico
    static int dañoPendiente = 0; // Daño pendiente para aplicar a la defensa del jugador

    // Inicialización del mapa irregular
    public static String[][] inicializacionMapa() {
        int filas = 5; // Número de filas en el mapa
        String[][] mapaIrregular = new String[filas][]; // Array irregular para el mapa
        mapaCopia = new String[filas][]; // Array copia del mapa

        int jefe = 0; // Control para la creación del jefe
        int fuente = 0; // Control para la creación de la fuente
        int inicio = 0; // Control para la creación del inicio
        int iteracion = 0; // Iterador para los enemigos

        for (int i = 0; i < filas; i++) {
            int columnas = (int) (Math.random() * 4) + 2; // Número aleatorio de columnas (entre 2 y 5)

            mapaIrregular[i] = new String[columnas]; // Inicializa cada fila con longitud distinta
            mapaCopia[i] = new String[columnas]; // Inicializa cada fila con longitud igual a mapaIrregular[i]

            for (int j = 0; j < columnas; j++) {
                // Distribuir elementos en el mapa
                if (jefe == 0) {
                    mapaIrregular[i][j] = "Jefe"; // Coloca el jefe en el mapa
                    mapaCopia[i][j] = "Jefe"; // Copia del jefe en el mapa
                    jefe++;
                } else if (fuente == 0) {
                    mapaIrregular[i][j] = "Fuente"; // Coloca la fuente en el mapa
                    mapaCopia[i][j] = "Fuente"; // Copia de la fuente en el mapa
                    fuente++;
                } else if (inicio == 0) {
                    mapaIrregular[i][j] = "Inicio"; // Coloca el inicio en el mapa
                    mapaCopia[i][j] = "Inicio"; // Copia del inicio en el mapa
                    inicio++;
                } else {
                    mapaIrregular[i][j] = "Enemigo_" + (iteracion); // Crea enemigos en el mapa
                    mapaCopia[i][j] = "Enemigo_" + (iteracion); // Copia de enemigos en el mapa
                }
                iteracion++;
            }
        }
        return mapaIrregular; // Retorna el mapa inicializado
    }

    // Mezcla aleatoria del mapa
    public static void mezclaMapa(String[][] map1, String[][] map2) {
        for (int i = 0; i < map1.length; i++) {
            for (int j = 0; j < map1[i].length; j++) {
                int il = (int) (Math.random() * map1.length); // Selección aleatoria de fila
                int jl = (int) (Math.random() * map1[il].length); // Selección aleatoria de columna

                // Cambio del contenido de mapa[i][j] y mapa[il][jl]
                String temp = map1[i][j];
                map1[i][j] = map1[il][jl];
                map1[il][jl] = temp;

                // Mezcla el mapaCopia de igual forma que map1
                temp = map2[i][j];
                map2[i][j] = map2[il][jl];
                map2[il][jl] = temp;
            }
        }
    }

	// Interfaz del jugador
	public static void interfazJugador() {
		System.out.println();
		System.out.println();
		System.out.println("                                                 .::::::::..");
		System.out.println("                                               ::...:::::::::.");
		System.out.println("                                             .::..::::....:-:::");
		System.out.println("                                             :::..--:.  ..-=:.-.");
		System.out.println("                                            .-::.:-:.....-=-::--.");
		System.out.println("                                            .-:::::...::--:..-=-:");
		System.out.println("                                            .-=-:::::::--::-:-=--....");
		System.out.println("                                             :=+=---=----==----:..:::::::.");
		System.out.println("                                         ..:::-=--::----::--::.........::-:.");
		System.out.println(".::::..                                ::::.::::..:-=-..::-::::..:::::..:::-.");
		System.out.println("-===----..                           .::..::::. ..:-==-::::::---:::::::..:---.");
		System.out.println(" .-+**+-:::.                        .-:::---:.  ..:=-:-=::-::.:--:..::::..::-.     .:::::..");
		System.out.println("   .-===-::::..                    .::--::::.   ..-==-::.:===-::.....:---::::.   .-::...:::::.");
		System.out.println("      :-==-:..::..                 ::..::::.   ..:-=---:.:=+++==----=++===--.   .-::---::::::::.");
		System.out.println("        .:----:..:::.             .-:.:--:.......-=-::.::.:=+*****+++++=:..    .-::--::.:::::::-:.");
		System.out.println("           .:----::.::.           :::-=+-:.......-==+==--:::-=+*++++++++=:.    ::......:::...::::-:");
		System.out.println("              .:---::.....         :-=+=-:...::.:-==-----==-----+++++===++=-:::-:.::---:::.. .::::-:");
		System.out.println("                 .:----::::-:.    .-====-:...::.::---::::-=----==++*+++++++*+=::..--------.. ..:--:-.");
		System.out.println("                   .::--=------:::=+++++-...:--:::---::::---:::=--=+*++++++**=::.:-:.......  .:::---:");
		System.out.println("                     .:--------:=+++++++-:.::-=-::---:::----:::=-::+****+***=:::..::..       .--:.:-:");
		System.out.println("                       ...::---:=**++=++-:.:-==-::+==--::::::::--. :===--==-::::..:::..      .:---.-:");
		System.out.println("                          ..:----+***+++-::-==--==-=-::-=-----::--         ..-::....::...   ...:--:-:");
		System.out.println("                     ..:----=--==+++++++==-+---=+=-=::--==-----::=:          :-.:::--:....  ...::::-.");
		System.out.println("                   :-===--:::::----===+++==++++=++-=:-+-:-=-:::-::-:         :-.:---:...   ...::::-:");
		System.out.println("              .:::-==-----::::::-::-----==+++++++**+=+++-:-=-:::-::==-:      :-.::::..    ..:::-::-:");
		System.out.println("           .:-=====----===::::-------::::---=======++++++-:-=-:::::===+-.    :-.::::..    ..::..:--.");
		System.out.println("         :-=====----===++==-:::---------:::::---======++++-:---::::.--==:    :-.:::::.   ..::-:::-:");
		System.out.println("        -=======--=====+===---:::---====----::::-----==+++=:-=-:::-.:--=:    :-.....:......::--:-:");
		System.out.println("      .-=======---====++==------:::---======----::::--==+++.-=--:--.:---:    .-:.:--::..:-:.:::-:.");
		System.out.println("     .-===-====--=====+++==---=----:----===++===----:--==++:-==----.:---:    .-:.:=-:...:-=:::-:.");
		System.out.println("    .-==---==----=====+++===---==---------===++===-----==++:-=====-:----:     --:.::...:::-::-:");
		System.out.println("    -==---==----===+=++++===------==--------===+++=======+=-:::...::---=:     :-::-:..::::::-:");
		System.out.println("   .-=----==---===++++++++===------====-------==++++++==+++=========--==:     .--:::..:-----.");
		System.out.println("   .-=---==---===++++++++++====-------=====-----===+++++++++++++++==--+=.      :-:::::----:");
		System.out.println("   .--:--==---===+++++++++++======--------=====-========+++++++===----==.       :-::----:.");
		System.out.println("   .-----==---==+++*+++++++++++++========----=================--------==:        .--::.");
		System.out.println("   .==---==--===+++++++++++++++=================+++++++++++===----======:");
		System.out.println("   .-==--=====================----------------==============++++==-----=:");
		System.out.println("    :===========---==+=+++++=-:::::::-=+===============:   .=+==-:.....::");
		System.out.println("    .===========:.:--===++=:         :+=------:...:::--    .-=-::.. ...-:");
		System.out.println("     :=+========:::--==+=:          .--:...........:==.     .-==:.....-:");
		System.out.println("     .-+=======:::--===-.          .::.   ...  ..::-=:       .-=-:...:-.");
		System.out.println("      .=+++=+==::-===-.            .-:.. .......:-=+-.        .-=:..:-.");
		System.out.println("       :=+++++=--=+=:              .-:........::-=+=.          -=: .:-.");
		System.out.println("        -+++++====:.                .--::....:-=+++:           :=:..:-.");
		System.out.println("        .=++++++-.                   :---:..:-++++=.           .=-..:-.");
		System.out.println("         -==-=+=.                     :=:..-=++++=.            .=-..:-.");
		System.out.println("        .--..-=:                      :-...-+++++=.            .=-...-.");
		System.out.println("        .--..:=.                      :-. .-=++++-             .=-...-:");
		System.out.println("        .-:..:=:                      :-...-=+++=:             .=-...:-.");
		System.out.println("        .-:..:=:                      ::...-=+++-              .--.. .::");
		System.out.println("        :-...:=:                      :-...-++++-              :-:....::");
		System.out.println("       ::.. .:--.                     :-...-++++=.             :-:.....::");
		System.out.println("      .-:.....::-.                   .--...-=++++=:            :--:.....::");
		System.out.println("      .--:......::                  .::. ..:-=++++=-.          .==--::...:.");
		System.out.println("       :==-:....:-.                .:........:-=+++=.          .-=-::....:-:");
		System.out.println("       .--::::::.::.                :-::.......-=++-.          .====--::::.::.");
		System.out.println("       .-=+***+-:..::               .:=--:..  ..:==.           :=+*****=-:. :-.");
		System.out.println("        .=+****=-:.::                .-=--::....::-:           :=+*****+=-...-.");
		System.out.println("         .:-====---:.                .-++****=:....::.         :=+++++++==-:::");
		System.out.println();
	}
	
	// Estadísticas según el nivel del enemigo
    public static void estadisticasEnemigo() {
        // Ajusta el nivel del enemigo basado en el nivel del jugador
        if (nivelJugador >= 2)
            nivelEnemigo = (int) ((Math.random()*3) + nivelJugador-1);
        if (mapa[filaActual][columnaActual].equals("Jefe"))
            nivelEnemigo = nivelJugador + 3; // El jefe es más fuerte que el jugador
        dañoEnemigo += (4) * (nivelEnemigo - 1); // Incrementa el daño del enemigo según su nivel
        maximoBarraEnemigo += (12) * (nivelEnemigo - 1); // Incrementa la vida máxima del enemigo
        vidaEnemigo = maximoBarraEnemigo; // Reinicia la vida del enemigo a su máximo
    }

	// Tipo de enemigos
	public static void interfazEnemigo() {
		String espaciosRepetidos = " ".repeat(85);
		
		switch (mapa[filaActual][columnaActual]) {

		case "Jefe":
			// Arte ASCII del Jefe
				System.out.println();
				System.out.println();
                System.out.println(espaciosRepetidos + "                                    ...                  ..");
                System.out.println(espaciosRepetidos + "                                 .-----::=            ------:.");
                System.out.println(espaciosRepetidos + "                                 --=+++=-::*#=-+####%*++++=-::#");
                System.out.println(espaciosRepetidos + "                               :==+******=---+*%%%%@@%#*****=-:");
                System.out.println(espaciosRepetidos + "                               -+**#%%%%%=--=+*%%%%@@@@%%%%%**==.");
                System.out.println(espaciosRepetidos + "                               =*#%#*%@#*=-++##%%%%%%@#*****%%**=");
                System.out.println(espaciosRepetidos + "                              +#%#+=.+@#++*##%%**%%%@@+.....%@%#+");
                System.out.println(espaciosRepetidos + "                            :+#%@*...+@#***%@%#+*@%*#@+.....=+%%#+");
                System.out.println(espaciosRepetidos + "                            =*%%+-...+@#***%@%%@@@%*#%#+:....:%@%#+");
                System.out.println(espaciosRepetidos + "                            =#@#...-*#%@@%%@@@@@@%%@@%@@=.....=+%%#+");
                System.out.println(espaciosRepetidos + "                             %=-...=@@%@@@%@@##@@%@@@%@@=......:@@#");
                System.out.println(espaciosRepetidos + "                             %:..-#%@@@@@##%%**%%@@@@%@@=.......");
                System.out.println(espaciosRepetidos + "                             @###*-*@@@@@%#%%%%%%@@@@@@@%###:..");
                System.out.println(espaciosRepetidos + "                           -%@@%--:--*@@@%%****%%@@@@@@@+-#@:..");
                System.out.println(espaciosRepetidos + "                        .:---=%%#*---::*@@@%%%%%%@@@@@+:--#@%#::");
                System.out.println(espaciosRepetidos + "                      --#%*+++=+@%#*+=-::#@@@%%@@@@@*::-*#%%==%#:");
                System.out.println(espaciosRepetidos + "                     %*+=-##*++*%%%%%*+=-#@@@@@@@@#::-=+#%=-++--::");
                System.out.println(espaciosRepetidos + ":##                .#%%%*++*#%%#=+%%%%%**%@@@%%%%@%+***#+=++++-=@#:-            .=");
                System.out.println(espaciosRepetidos + "@#-+            -::-=+#%%%**%%%%%#=*%%%@@%%%%%%%%@@%%%*=#%****%%%#--:+@#*#.  -#==%");
                System.out.println(espaciosRepetidos + "@%+=-*@@@%%##::::-----=+%@%%@%*+-+@#=#@%%=-::::-=%%@#=*%%%%%%%@%-=++-+%%@%###==%%@");
                System.out.println(espaciosRepetidos + "+#*+=*#%@@@-:-----=+++**#%%@@@%%*#@@@@@#+=------=+*@@@#*=-%@%%@%**+++=-#@=:--**%%#");
                System.out.println(espaciosRepetidos + " =###*+%@##---=+++****#%%%%%@@@@@@%%@%%#*++====++*#%%@@%##%@@@%%%#**+=-*#++**%%@%=");
                System.out.println(espaciosRepetidos + "    =+###++==++***#%%%%%@@@@@@##@%##%%%%%#******#%%%@@@@@@%%@@%%%%%#**+=-+#%%@@###");
                System.out.println(espaciosRepetidos + "     +===%@#**#%%%%%%%@@@@@@@%**%%%#*#%%%%%%%%%%%%%%%%@@@@%%@@@@@@%%%#*+=++****==");
                System.out.println(espaciosRepetidos + "          ###%%%%%%%%%@@@@@@@@@@@%=+*#@%#**#############%%@@@@%%@@@@@@@@%%%#*%@+===*");
                System.out.println(espaciosRepetidos + "          #%%%%%%%@@@@@@@@@@@@@@@%--+*@@%##**##########%@@@@@%%%@@@@@@%@@@%%%%%###=");
                System.out.println(espaciosRepetidos + "          +**%@@@@@@@@@@@@@@@@@@@%--+*%%@%%#**#%@@@@@@%%@@@@%%#*@@@@@@%%@@@@@%%###");
                System.out.println(espaciosRepetidos + "          =%%@@@@@@@@@@@@@@@@@@@@%::-=*#@%%##**######%%@@@@@%%*+%@@@@@%%@@@@@@@+");
                System.out.println(espaciosRepetidos + "           @@@@%%@@@@@@@@@%%@@@@@%:-##+*%%@%%%#######%%@@@%%#*++%@@@@@%%@@%@%");
                System.out.println(espaciosRepetidos + "           @@@@%%%%@@@@@@@@@@@@@@@#*---=*#@@@%%%%%%@@@@@@@#*#%==%@@@@@%%@@%@@");
                System.out.println(espaciosRepetidos + "          +@@#*%%%%@@@@@@@%%@@@@@@@@%*::-+*%@@@@@@@@@@@@#=--==#%@@@@@@%%@%#%@");
                System.out.println(espaciosRepetidos + "          %@@**%%%%@@@@@@@%%@@%%@@@@%%%%%%@@@@@@@@@@@@@@@%%%%%@@@@@@@@@@%%@%#");
                System.out.println(espaciosRepetidos + "         -#%@**%%%%@@@@@@@%%@@%%@@@@%#*+-*@@@@@@@@@@@@@@#*#%@@@@@@@@@@@@%%@%#");
                System.out.println(espaciosRepetidos + "         %%@@**%%%%%%@@@@@%%%@%%@@@@%%@#+=---::::::::-=*#@%%@@@@@@@@@@@@%%@%#");
                System.out.println(espaciosRepetidos + "         %%@@****%%%%@@@@%%%%%@@%%@@%%%%%#+++--:.::-=+*%%%@@@@@@@@@@@@@@%%@%#");
                System.out.println(espaciosRepetidos + "         %%@@****%%%%@@@@@%%%%%@%%@@@@@@%%%#*==--==**%%%@@@@@@@@%%@@@@@@%%@@@%-");
                System.out.println(espaciosRepetidos + "         %%@@****%%%%@@@@%%%%%%%%%%%@@%#*#%%%######%%%#*#%%@@@@@%%@@@@@@%%@@@%#");
                System.out.println(espaciosRepetidos + "         %%@@##**##%%@@@@%%%%%%%%%%@@@%%%#+=-::..::-=+*%%%%@@@@@%%@@@@@@%%@@@%#");
                System.out.println(espaciosRepetidos + "         %%%%@@****%%%%@@@%#%%%%%%%%@@%%@#**+--::--++*#@%%@@@@@@%%@@@@@@%%@@@%%#");
                System.out.println(espaciosRepetidos + "         #%%%@@****%%%%@@@%#%%%%%%%%%@@@%%%#*==::-=*#%%%@@@@@@%%%%@@@@@@%%@@@@@##");
                System.out.println(espaciosRepetidos + "          #%%@@****%%%%@@@%#%%%%%%%%%%@@%%#%%##**##%%#%%@@@@@@%%%%@@@@@@%%@@@@@%%%");
                System.out.println(espaciosRepetidos + "          #%%@@****%%%%@@@%#%%%%%%%%%%@%%####*+++++####%%@@@%%%%%%@@@@@@%%@@@@@@@#");
                System.out.println(espaciosRepetidos + "          %%%@@****%%%%@@@%##%%%%%%%%%%%%@@#+-::.:-+*%%%@@@@%%%%@@@@@@@@%%@@@@@@@%%+");
                System.out.println(espaciosRepetidos + "          #%%@@****%%%%@@@%###%%%%%%%%%%@@%%##****##%@@@@@@%%%%%@@@@@@@@%%%@@@@@@@@##");
                System.out.println(espaciosRepetidos + "           =%@@****%%%%@@@%###%%%%%%%%%%%%%#*********%%@@@%%%%%%@@@@@@@@%%%@@@@@@@@##");
                System.out.println(espaciosRepetidos + "            %@@****%%%%@@@%###%%%%%%%%%%%#*++++++++++*#%%%%%%%%%@@@@@@@@%%%@@@@@@@@%%*");
                System.out.println(espaciosRepetidos + "            %@@****%%%%@@@%###%%%%%%%%%%%#+++=-----+++*%%%%%%%%@@@@@@@@@%%%@@@@%%@@@@@%#");
                System.out.println(espaciosRepetidos + "            =%@****%%%%@@@%###%%%%%%%%%%%#+++=-----+++*%%%%%%%%@@@@@@@@@%%%@@@@%%@@@@@@@%#");
                System.out.println(espaciosRepetidos + "               +***%%%%@@@%#####%%%%%%%%%#+=--------=+*%%%%%%%@@@@@@@@@@%%%@@@@%%@@@@@@@%#");
                System.out.println(espaciosRepetidos + "                ***%%%%@@@%#####%%%%%%%%%#+=---------+*%%%%%%%@@@@@@@@@@%%%@@@@%%@@@@@@@@%#");
                System.out.println(espaciosRepetidos + "                ***%%%%@@@%#####%%%%%%%%%+---:..::----+%%%%%%%@@@@@@@@@@%%%@@@@%%%%@@@@@@@@@%#");
                System.out.println(espaciosRepetidos + "                **%%@@%%%%@%#####%%%%%%%%%+---:..::----+%%%%%%%@@@@@@@@@@%%%@@@@%%%%@@@@@@@@@@%");
                System.out.println(espaciosRepetidos + "                **%%@@%%%%@%#####%%%%%%%%%+---:..::----+%%%%%%%@@@@@@@@@@%%%@@@@%%%%@@@@@@@@@@%");
                System.out.println(espaciosRepetidos + "              -%@@%%%%%%%%@%#####%%%%%%%%%+---:..::----+%%%%%%%@@@@@@@@@@%%%@@@@%%%%@@@@@@@@@@%");
                System.out.println(espaciosRepetidos + "              -%@@%%%%%%%%@%#####%%%%%%%%%+---:..::----+%%%%%%%@@@@@@@@@@%%%@@@@%%%%@@@@@@@@@@%");
			break;
			
		default:
			// Arte ASCII del enemigo común
				System.out.println();
				System.out.println();
			    System.out.println(espaciosRepetidos + "..                                                                                .    .:.");
			    System.out.println(espaciosRepetidos + "-====--:.  ..                                                                    -== :=+===");
			    System.out.println(espaciosRepetidos + ".=====+++=-+--                                                                 .---+-++++++");
			    System.out.println(espaciosRepetidos + "  -+++++=+*+=--.                                                               ====+=-=++**");
			    System.out.println(espaciosRepetidos + "   +#*+*=-=++=+=..                                                         .-=*+++====+++++");
			    System.out.println(espaciosRepetidos + "   -=++=++===+++#--.                                                       -:=*+++======+++");
			    System.out.println(espaciosRepetidos + "    :+*+===++++++-:-                                                      :=-=*++++===+*-..");
			    System.out.println(espaciosRepetidos + "     :*#+==++++++=-=:  ..                                            .---==--+==++++++*#=:.");    
			    System.out.println(espaciosRepetidos + "    .=+++++++++--=-=++=--:                                          :=::=+==---+*++++++==-");
			    System.out.println(espaciosRepetidos + "     .:=++++++*+--===+-::-:                                        :--::=+++=--*+++++++-:");
			    System.out.println(espaciosRepetidos + "       ..-=++***--=+++=::---.                                    .=---:-++++===**+++-:");
			    System.out.println(espaciosRepetidos + "          .-++**==++++==----=:                                 :-=----==-=+++==+*++-");
			    System.out.println(espaciosRepetidos + "           .++**+=+++-:===---==-.                            :===---===-.-++==+**++=.");
			    System.out.println(espaciosRepetidos + "            -++**=+++-:--==---==+=.                        .=+==------:::+**+++++*=.");
			    System.out.println(espaciosRepetidos + "            .+++*++**+-::------====:   :-        ::.      .====--------=+**++++++*:");
			    System.out.println(espaciosRepetidos + "            .-=+++++**++=-------====: .-+.      .:-.     .=+===-----==++++**+++==:");
			    System.out.println(espaciosRepetidos + "              .-=++++=====-----====+=:-=*=. .   --=.     =++====--------:-*+=-..");
			    System.out.println(espaciosRepetidos + "                 .-=+=:::--------===+=-+**+===-:-=+.    :+==-------::::-=++:");
			    System.out.println(espaciosRepetidos + "                   .+++=------------++++*+====+*=++- ..:+++===---=====++++=.");
			    System.out.println(espaciosRepetidos + "                   .==+*+========-:+*+++=--=++**++====++*+==----------==-:.");
			    System.out.println(espaciosRepetidos + "                      .+=-:-++===:-*#++-:.:-++***+-===***+====---:--=++:");
			    System.out.println(espaciosRepetidos + "                   :---++++++=--:.-*##+-:.:=+**+*#==:=++====+=---=+=-:.");    
			    System.out.println(espaciosRepetidos + "                :-++++-::.:=+=::..-*##*+--=+=***##+-:----::-==::-=-");    
			    System.out.println(espaciosRepetidos + "              :+***++=::..:-==-:::-+##=+*###++-*++---=--::.:-==++=");    
			    System.out.println(espaciosRepetidos + "            .+#***##***++--===---::=*#+**+-=+==***=-:=+==---=**=.");    
			    System.out.println(espaciosRepetidos + "            -+*####*****##*=--:::=++*#+*#*+**++###***+=--:-=++-");    
			    System.out.println(espaciosRepetidos + "           :=+###*****+*++**-::.=**##*=+****+==*###*##*****+=+:");    
			    System.out.println(espaciosRepetidos + "           +*####*=-=++==-=**:...:-**=------:.:-*#####*++#****-");    
			    System.out.println(espaciosRepetidos + "          .+####*+:.:=-::.:+*-...:-*#*=-::::-==+*###%*++**+++++=.");    
			    System.out.println(espaciosRepetidos + "          .+#####*++*#+=-:-+*-..:++#####**++*****++###**=:::-=+++:");    
			    System.out.println(espaciosRepetidos + "           -*####**##*+++++#+:..:+*%#*******+++=--=*##*=-:..::-=++.");    
			    System.out.println(espaciosRepetidos + "           .+#####*********=::::. .=*+=+=--+-:-+++***#*+=-:::::-==.");    
			    System.out.println(espaciosRepetidos + "            :*#**###**#**+::--:.    =*+*+--*+==****++*#*+=-:::::-==.");    
			    System.out.println(espaciosRepetidos + "             .+**+++====-::--:       =*##***#***+++**###**+--:::-===.");    
			    System.out.println(espaciosRepetidos + "               .=+*+=-:===-:        .=*******+==+*#######**++===---==.");    
			    System.out.println(espaciosRepetidos + "                  :===--:.       .=+**##*+-:::-=*###***#######*+===-=-.");    
			    System.out.println(espaciosRepetidos + "                                -##**#####+=-=++=====+**###**#*==++=-:-:");    
			    System.out.println(espaciosRepetidos + "                             .::*#**######*++=-:.....:+#####**#*=-+*+-:--.");    
			    System.out.println(espaciosRepetidos + "                           .:::.:+#####*-:+*+=-:.:::::-*#####**##=:-++==++.");    
			    System.out.println(espaciosRepetidos + "                          .----..:=##*=.  .-++==-..:--=*%#####**#*=-- .:=**-");    
			    System.out.println(espaciosRepetidos + "                          -:-=+-:-=+:.       .:=*==++==--+####**++*+=    .=*=:");    
			    System.out.println(espaciosRepetidos + "                          :-*#*=--*+           :**++++.   .-*##+=+##+      :++=:");    
			    System.out.println(espaciosRepetidos + "                            .**+==*=           .+*=+*-       :***###*       .=**-.");    
			    System.out.println(espaciosRepetidos + "                             .-+***=           .##+*#:        -#####*.        .=--:.");    
			    System.out.println(espaciosRepetidos + "                               :*##*.          =##+*#:         -#####:         .-::=.");    
			    System.out.println(espaciosRepetidos + "                               .*####=-:.     .*##**#-          :*####:          :===.");    
			    System.out.println(espaciosRepetidos + "                                =####*=--     :##*++*=           .*###*-.          ..");    
			    System.out.println(espaciosRepetidos + "                                .+###+==-     -##*++**.           :#####*-");    
			    System.out.println(espaciosRepetidos + "                                 .-++++=.     -=*+==##+            =###***.");    
			    System.out.println(espaciosRepetidos + "                                     ..       :-:..:+##-            =#*++*:");
			break;
		}
	}
	
	// Interfaz barra transcurso del nivel
    public static void interfazBarraNivel() {
        int maximoBarraNivel = 100 + (30 * (nivelJugador-1)); // Calcula el máximo de la barra de nivel

        int porcentajeVida = (barraNivelProgresado * 100) / maximoBarraNivel; // Porcentaje de progreso

        int cantidadVerde = (porcentajeVida * maximoBarraNivel) / 100; // Cantidad de vida representada en verde
        int cantidadGris = maximoBarraNivel - cantidadVerde; // Cantidad restante en gris

        System.out.println(VERDE + "█".repeat(cantidadVerde/2) + GRIS + "█".repeat(cantidadGris/2) + RESET + "\t" + barraNivelProgresado + "/" + maximoBarraNivel);
        System.out.println();
        System.out.println();
    }

    // Transcurso de los niveles del jugador
    public static void nivelesJugador(int experiencia) {
        barraNivelProgresado += experiencia; // Suma la experiencia ganada
        int maximoBarraNivel = 100 + (30 * (nivelJugador-1)); // Calcula el máximo de la barra de nivel
        System.out.println("Has conseguido " + VERDE + experiencia + " puntos de experiencia" + RESET);
        if (barraNivelProgresado >= maximoBarraNivel) { // Verifica si se ha alcanzado el máximo
            nivelJugador++; 
            int temp = barraNivelProgresado - 100; // Ajusta el progreso de la barra
            barraNivelProgresado = temp; // Actualiza el progreso
            System.out.println();
            System.out.println(VERDE + "\nEnhorabuena!! has subido un nivel" + RESET + ", tus estadísticas han subido");
            System.out.println();
            dañoJugador += 10; 
            defensa += 15;
            maximoBarraJugador += 30; 
            vidaJugador += 40; 
            defensa = (defensa > 100) ? 100 : defensa; // Limita la defensa a 100
            
            // Mensaje de actualización de estadísticas
            System.out.println("Su daño ha aumentado en " + ROJO + "10" + RESET + " puntos, ahora tiene " + ROJO + dañoJugador
                    + RESET + " puntos de daño, su vida máxima también ha aumentado, ahora tiene " + ROJO + maximoBarraJugador + RESET
                    + " puntos máximos de vida");
            System.out.println("Su vida actual ha aumentado " + ROJO + "40" + RESET + " puntos");
        }
    }
	
    // Método para gestionar el ataque del enemigo
    public static void procesarAtaqueEnemigo(int daño) {
        int dado = (int) ((Math.random() * 20) + 1);
        boolean ataqueCritico = (dado == 20);
        boolean ataqueExitoso = (dado >= 11);

        System.out.println();
        System.out.println(AZUL + "(" + mapa[filaActual][columnaActual] + ")" + RESET + " ha tirado el dado, dando como resultado " + dado);

        if (dado == 1) { // Fallo crítico
            int dañoAutoinfligido = (int) (daño / 2);
            System.out.println("\n" + mapa[filaActual][columnaActual] +
                               " ha fallado estrepitosamente, infligiéndose " + ROJO + dañoAutoinfligido + RESET + " puntos de daño");
            vidaEnemigo -= dañoAutoinfligido;
        } else if (ataqueExitoso) { // Éxito
            int dañoFinal = ataqueCritico ? (int) (daño * 1.5) : daño;
            dañoPendiente = dañoFinal; // Marcar daño pendiente para la defensa
            System.out.println("\n" + mapa[filaActual][columnaActual] + " ha lanzado un ataque " +
                               (ataqueCritico ? "crítico" : "exitoso") +
                               " infligiendo " + ROJO + dañoFinal + RESET + " puntos de daño");
        } else { // Fallo normal
            System.out.println("\n" + mapa[filaActual][columnaActual] + " ha fallado el ataque");
            dañoPendiente = 0; // No hay daño que procesar
        }
    }
    
    // Método para gestionar el ataque del jugador
    public static void procesarAtaqueJugador(int daño) {
        int dado = (int) ((Math.random() * 20) + 1);
        boolean ataqueCritico = (dado == 20);
        boolean ataqueExitoso = (dado >= 11);

        System.out.println();
        System.out.println(AZUL + "(" + nombreJugador + ")" + RESET + " ha tirado el dado, dando como resultado " + dado);

        if (dado == 1) { // Fallo crítico
            int dañoAutoinfligido = (int) (daño / 2);
            System.out.println("\n" + nombreJugador +
                               " ha fallado estrepitosamente, infligiéndose " + ROJO + dañoAutoinfligido + RESET + " puntos de daño");
            vidaJugador -= dañoAutoinfligido;
        } else if (ataqueExitoso) { // Éxito
            int dañoFinal = ataqueCritico ? (int) (daño * 1.5) : daño;

            // Verificar si ambos ataques son letales
            if (dañoPendiente > 0 && vidaJugador - dañoPendiente <= 0 && vidaEnemigo - dañoFinal <= 0) {
                // Activar la mecánica especial
                vidaJugador = 1; // El jugador sobrevive con 1 punto de vida
                vidaEnemigo -= dañoFinal; // El enemigo recibe daño letal
                System.out.println("\n¡" + nombreJugador + " realiza un último ataque heroico y derrota a " + mapa[filaActual][columnaActual] + " en un duelo épico!");
                dañoPendiente = 0; // Anular daño pendiente del enemigo
            } else {
                vidaEnemigo -= dañoFinal; // Aplicar daño normal al enemigo
                System.out.println("\n" + nombreJugador + " ha infligido " + ROJO + dañoFinal + RESET + " puntos de daño al enemigo");
            }
        } else { // Fallo normal
            System.out.println("\n" + nombreJugador + " ha fallado el ataque");
        }
    }

    // Método para gestionar la defensa
    public static void procesarDefensa() {
        int dado = (int) ((Math.random() * 20) + 1);

        System.out.println();
        System.out.println(AZUL + "(" + nombreJugador + ")" + RESET + " ha tirado el dado, dando como resultado " + dado);

        if (dañoPendiente > 0) {
            if (dado <= 10) { // Defensa parcial
                int porcentaje = (defensa * dañoPendiente) / 100;
                int dañoReducido = dañoPendiente - porcentaje;
                vidaJugador -= dañoReducido;
                System.out.println("\n" + nombreJugador + " ha defendido parcialmente el ataque, recibiendo " + ROJO + dañoReducido + RESET +
                                   " puntos de daño");
            } else { // Defensa exitosa
                System.out.println("\n" + nombreJugador + " ha defendido exitosamente el ataque, sin recibir daño");
            }
        } else {
            System.out.println("\nNo hay daño pendiente. ¡Nada que defender!");
        }
        dañoPendiente = 0; // Reinicia el daño pendiente tras procesar la defensa
    }

    // Método para manejar situaciones en las que no se defiende correctamente
    public static void procesarDañoSinDefensa() {
        if (dañoPendiente > 0) {
            vidaJugador -= dañoPendiente; // Aplicar el daño pendiente al jugador
            dañoPendiente = 0; // Reiniciar el daño pendiente
        }
    }

    // Método de movimientos de combate
    public static void movimientosCombate() {
        String repetirBarritas = "█".repeat(18);

        System.out.println();
        System.out.println(repetirBarritas);
        System.out.println("██   Defender   ██");
        System.out.println(repetirBarritas);

        System.out.println();
        System.out.println(repetirBarritas);
        System.out.println("██    Atacar    ██");
        System.out.println(repetirBarritas);
        System.out.println();

        System.out.println("Su poder de ataque actual es de " + ROJO + dañoJugador + RESET + " debido a que es nivel " + VERDE +
                           nivelJugador + RESET);
        System.out.println("Su poder de defensa actual es del " + AZUL + defensa + RESET + " debido a que es nivel " +
                           VERDE + nivelJugador + RESET);

        String accion;
        do {
            System.out.println("¿Qué quiere hacer? (Atacar/Defender)");
            accion = input.nextLine().toLowerCase();

            switch (accion) {
                case "atacar":
                    procesarAtaqueEnemigo(dañoEnemigo); // Ataque del enemigo
                    if (vidaJugador > 0) {
                    	procesarAtaqueJugador(dañoJugador); // Ataque del jugador
                        procesarDañoSinDefensa(); // Aplica daño pendiente si no se defendió
                    }
                    break;

                case "defender":
                    procesarAtaqueEnemigo(dañoEnemigo); // Ataque del enemigo
                    if (vidaJugador > 0) {
                        procesarDefensa(); // Defensa del jugador
                    }
                    break;

                default:
                    System.out.println("\nValor inválido.");
            }
        } while (!accion.equals("atacar") && !accion.equals("defender"));
    }

    // Permite saber la localizacion actual del jugador
    public static void localizacionActual() {
        String ubicacionMapa = "";
        String localizacion = "";

        // Si es la primera iteración, busca la ubicación "Inicio" en el mapa
        if (iteracionLocalizacionActual == 0) {
            for (int i = 0; i < mapa.length; i++) {
                for (int j = 0; j < mapa[i].length; j++) {
                    if (mapa[i][j].equals("Inicio")) {
                        // Guarda la ubicación y actualiza las coordenadas
                        localizacion = mapa[i][j];
                        ubicacionMapa = ", situado en la ubicación " + VERDE + "[" + i + "]" + "" + "[" + j + "]"
                                + RESET + " del mapa";
                        filaActual = i; // Actualiza la fila actual
                        columnaActual = j; // Actualiza la columna actual
                    }
                }
            }
            iteracionLocalizacionActual++; // Incrementa el contador de iteraciones
        } else {
            // En iteraciones posteriores, busca la ubicación actual guardada
            for (int i = 0; i < mapa.length; i++) {
                for (int j = 0; j < mapa[i].length; j++) {
                    if (mapa[i][j].equals(Localizacion)) {
                        // Guarda la ubicación y actualiza las coordenadas
                        localizacion = mapa[i][j];
                        ubicacionMapa = ", situado en la ubicación " + VERDE + "[" + i + "]" + "" + "[" + j + "]"
                                + RESET + " del mapa";
                        filaActual = i; // Actualiza la fila actual
                        columnaActual = j; // Actualiza la columna actual
                    }
                }
            }
        }
        Localizacion = localizacion; // Actualiza la variable de localización
        System.out.print(VERDE + localizacion + RESET + ubicacionMapa); // Muestra la localización actual
    }

 // Saca el mapa por pantalla
    public static void salidaMapa(String[][] mapa) {
        // Imprime líneas en blanco para separar la salida
        System.out.println();
        System.out.println();
        System.out.println();
        
        // Imprime el título del juego
        System.out.println(VERDE + "===============" + RESET + AZUL + " Dungeons and Realms " + RESET + VERDE + "================" + RESET);
        
        // Itera sobre cada fila del mapa
        for (int i = 0; i < mapa.length; i++) {
            // Imprime una línea de separación
            System.out.print(VERDE + "____________________________________________________________\n" + RESET);
            
            // Itera sobre cada columna de la fila actual
            for (int j = 0; j < mapa[i].length; j++) {
                // En la primera iteración, encuentra la posición de "Inicio"
                if (iteracionSalidaMapa == 0) {
                    iteracionSalidaMapa++; // Se incrementa el contador de iteraciones
                    for (int x = 0; x < mapa.length; x++) {
                        for (int y = 0; y < mapa[x].length; y++) {
                            // Si encuentra "Inicio", actualiza las coordenadas actuales
                            if (mapa[x][y].equals("Inicio")) {
                                filaActual = x;
                                columnaActual = y;
                            }
                        }
                    }
                }
                // Imprime el símbolo del mapa en verde si es la posición actual del jugador
                if (i == filaActual && j == columnaActual) {
                    System.out.print("|" + VERDE + mapa[i][j] + RESET + "|");
                } 
                // Imprime el símbolo en rojo si ha cambiado desde la última copia
                else if (!mapaCopia[i][j].equals(mapa[i][j])) {
                    System.out.print("|" + ROJO + mapa[i][j] + RESET + "|");
                } 
                // Imprime el símbolo del mapa sin cambios
                else {
                    System.out.print("|" + mapa[i][j] + "|");
                }
            }
            // Imprime una línea de separación al final de la fila
            System.out.print(VERDE + "\n------------------------------------------------------------" + RESET);
            System.out.println();
        }
        // Reinicia las coordenadas actuales al final de la salida del mapa
        filaActual = 0;
        columnaActual = 0;
    }

    // Permite asignar el nombre al jugador
    public static String CambiarNombre() {
        // Solicita al jugador que ingrese su nombre
        System.out.print("\n\nIngrese su " + AZUL + "Nombre: ");
        nombreJugador = input.nextLine(); // Lee el nombre ingresado
        System.out.print(RESET + "Su nombre ha sido guardado correctamente"); // Confirma que el nombre fue guardado

        return nombreJugador; // Retorna el nombre del jugador
    }

    // Genera una barra de vida visual
    public static String barraVida(int vida, int maximoVida, int maximoBarra) {
        // Calcula el porcentaje de vida actual
        int porcentajeVida = (vida * 100) / maximoVida;

        // Calcula la cantidad de segmentos verdes y rojos en la barra
        int cantidadVerde = (porcentajeVida * maximoBarra) / 100;
        int cantidadRoja = maximoBarra - cantidadVerde;

        // Retorna la representación visual de la barra de vida
        return VERDE + "█".repeat(cantidadVerde) + ROJO + "█".repeat(cantidadRoja) + RESET;
    }

	
	// Método para mostrar la guía
	public static void mostrarGuia() {
	    System.out.println(VERDE + "\n\n============================================== " + AZUL + "GUÍA " + VERDE + " ==============================================" + RESET);
	    System.out.println();
	    System.out.println(AZUL + "Bienvenido a Dungeons and Realms!" + RESET);
	    System.out.println("En este juego, asumirás el papel de un valiente aventurero que explora un mundo lleno de peligros");
	    System.out.println("(En futuras actualizaciones se añadirán más pisos, cuando derrotes al jefe del piso podrás descender al piso inferior)");
	    System.out.println();
	    
	    System.out.println(VERDE + "Controles:" + RESET);
	    System.out.println("W - Mover hacia arriba");
	    System.out.println("A - Mover hacia la izquierda");
	    System.out.println("S - Mover hacia abajo");
	    System.out.println("D - Mover hacia la derecha");
	    System.out.println();
	    
	    System.out.println(VERDE + "Mecánicas del juego:" + RESET);
	    System.out.println("1. En cada sala, puedes encontrar enemigos, un jefe o una fuente");
	    System.out.println("2. Cuando encuentres un enemigo, deberás luchar contra él");
	    System.out.println("3. Puedes defenderte o atacar durante las batallas");
	    System.out.println("4. Si derrotas al Jefe, habrás completado el juego");
	    System.out.println();
	    
	    System.out.println(VERDE + "Uso de Dados:" + RESET);
	    System.out.println("1. Durante las batallas, se utiliza un dado de 20 caras (D20) para determinar el resultado de tus acciones");
	    System.out.println("2. Un resultado de 20 es un golpe crítico, infligiendo más daño");
	    System.out.println("3. Un resultado de 1 es un fallo crítico, causando daño al propio jugador");
	    System.out.println("4. La probabilidad de éxito de un ataque depende del resultado del dado y de las estadísticas del personaje");
	    System.out.println("(Los enemigos también usan esta mecánica)");
	    System.out.println();
	    
	    System.out.println(VERDE + "Progresión de Niveles:" + RESET);
	    System.out.println("1. A medida que avanzas, ganarás experiencia al derrotar enemigos y completar desafíos");
	    System.out.println("2. Al acumular suficiente experiencia, subirás de nivel, mejorando tus estadísticas");
	    System.out.println("3. Cada nivel te permitirá aumentar tu poder en combate");
	    System.out.println();
	    
	    System.out.println(VERDE + "Mapa Aleatorio:" + RESET);
	    System.out.println("1. El mapa del juego se genera de manera aleatoria, lo que significa que cada partida es única");
	    System.out.println("2. Las salas y los enemigos se colocan en diferentes ubicaciones en cada nueva partida");
	    System.out.println();
	    
	    System.out.println(VERDE + "Consejos de Combate:" + RESET);
	    System.out.println("- Siempre revisa tu salud antes de entrar en combate");
	    System.out.println("- Usa la fuente para restaurar tu salud cuando sea necesario");
	    System.out.println("- Si fallas un ataque, no te desanimes, ¡la próxima vez puede ser diferente!");
	    System.out.println();
	    
	    System.out.println(AZUL + "¡Buena suerte, aventurero!" + RESET);
	    System.out.println(VERDE + "\n====================================================================================================" + RESET);
	    
	    // Esperar a que el jugador presione una tecla para volver al menú
	    System.out.println("\n\nPresiona " + AZUL + " Enter " + RESET + " para volver al menú principal");
	    input.nextLine();
	    menuInicio();
	}
	
	// Menú de inicio
	public static void menuInicio() {
	    String opcion = ""; // Inicializa la variable para la opción seleccionada

	    do {
	        // Imprime líneas en blanco para separar la salida
	        System.out.println("");

	        // Imprime el título del juego y las opciones del menú
	        System.out.println(VERDE + "================" + AZUL + " Dungeons and Realms " + VERDE + " ===============" + RESET);
	        System.out.println("1. Jugar");
	        System.out.println("2. Guía");
	        System.out.println("3. Cerrar juego");
	        System.out.println(VERDE + "=====================================================" + RESET);

	        // Lee la opción elegida por el usuario
	        opcion = input.nextLine();

	        // Evalúa la opción seleccionada
	        switch (opcion) {
	            case "1":
	                juegoPrincipal(); // Llama al método para iniciar el juego
	                break;
	            case "2":
	                mostrarGuia(); // Llama al método para mostrar la guía
	                break;
	            case "3":
	                System.out.println("Cerrando el juego..."); // Mensaje de cierre
	                System.exit(0); // Termina el programa
	                break;
	            default:
	                System.out.print("\nValor inválido\n"); // Mensaje de error para opción no válida
	        }
	    } while (!opcion.equals("1") && !opcion.equals("2")); // Repite hasta que se seleccione 'Jugar' o 'Guía'
	}

	// Método para mostrar la pantalla de batalla
	public static void pantallaBatalla() {
	    estadisticasEnemigo(); // Muestra las estadísticas del enemigo
	    // Informa al jugador sobre el inicio de la batalla
	    System.out.println("\nVa a comenzar una pelea contra el " + mapa[filaActual][columnaActual] + " presiona " + AZUL + " Enter " + RESET + " cuando esté preparado");
	    input.nextLine(); // Espera a que el jugador esté listo

	    // Ciclo de batalla que se ejecuta mientras ambos, jugador y enemigo, tengan vida
	    while (vidaJugador > 0 && vidaEnemigo > 0) {
	        // Imprime líneas en blanco para separar la salida
	        System.out.println();
	        System.out.println();
	        
	        // Crea una línea de separación para la sección de batalla
	        String igualRepetido = "=".repeat(85);
	        System.out.println(VERDE + igualRepetido + " BATALLA " + igualRepetido + RESET);
	        System.out.println();
	        System.out.println();

	        // Muestra la información del jugador y del enemigo
	        System.out.print(nombreJugador + " NV " + nivelJugador + "    VS     " + mapa[filaActual][columnaActual]
	                + " NV ");
	        System.out.println(mapa[filaActual][columnaActual].equals("Jefe") ? "¿?" : nivelEnemigo);
	        System.out.println();

	        // Muestra la vida del enemigo y su barra de vida
	        System.out.println("Vida de " + mapa[filaActual][columnaActual] + ": "
	                + barraVida(vidaEnemigo, maximoBarraEnemigo, 50) + "\t" + vidaEnemigo + "/" + maximoBarraEnemigo);
	        interfazEnemigo(); // Muestra la interfaz del enemigo

	        // Muestra la vida del jugador y su barra de vida
	        System.out.println("Vida de " + nombreJugador + ": " + barraVida(vidaJugador, maximoBarraJugador, 50) + "\t"
	                + vidaJugador + "/" + maximoBarraJugador);
	        interfazJugador(); // Muestra la interfaz del jugador

	        // Imprime otra línea de separación
	        System.out.println(VERDE + igualRepetido + igualRepetido + RESET);
	        
	        // Llama al método para manejar los movimientos de combate
	        movimientosCombate();
	    }

	    // Mensaje si el jugador es derrotado
		if (vidaJugador <= 0) {
			System.out.println(ROJO + "\nHa sido derrotado, su legado será recordado" + RESET);
		} else if (mapaCopia[filaActual][columnaActual].equals("Jefe")) {
	        System.out.println(VERDE +"\n\nMuy bien " + AZUL + nombreJugador + VERDE
	                + " ha cumplido su cometido y ha derrotado al " + ROJO + " Jefe " + VERDE + " exitosamente,"
	                + " todos los guerreros que perecieron en esta sala están orgullosos de ti" + RESET);
	        
	        int enemigosDerrotados = 0; // Contador de enemigos derrotados
	        int numeroCasillas = 0; // Contador de casillas en el mapa
	        
	        // Cuenta el número total de casillas en el mapa
	        for (int i = 0; i < mapa.length; i++) {
	            for (int j = 0; j < mapa[i].length; j++) {
	                numeroCasillas++;
	            }
	        }

	        // Cuenta los enemigos derrotados
	        for (int i = 0; i < mapa.length; i++) {
	            for (int j = 0; j < mapa[i].length; j++) {
	                for (int x = 0; x <= (numeroCasillas-3); x++) {
	                    // Verifica si se ha derrotado a un enemigo
	                    if (mapa[i][j].equals("Enemigo_" + x) && !mapaCopia[i][j].equals(mapa[i][j])) {
	                        enemigosDerrotados++;
	                    }
	                }
	            }
	        }

	        // Muestra la cantidad de enemigos derrotados
	        System.out.println("Enemigos derrotados: " + VERDE + enemigosDerrotados + RESET);
	        System.out.println("Muchas gracias por jugar!!!\n");
	    } 
	    // Mensaje si el jugador derrota a un demonio
	    else {
	        System.out.println("\nHa derrotado al demonio, bien hecho");
	        mapaCopia[filaActual][columnaActual] = "0"; // Marca la casilla como vacía
	        // Resetea las estadísticas del enemigo
	        dañoEnemigo = 15;
	        maximoBarraEnemigo = 100;
	        vidaEnemigo = maximoBarraEnemigo;

	        int exp = 50; // Experiencia predeterminada que ganará el jugador al ganar el combate
	        // Calcula la experiencia ganada según el nivel del enemigo
	        if (nivelEnemigo > nivelJugador) {
	            nivelesJugador(exp + 30);
	        } else if (nivelEnemigo == nivelJugador || nivelEnemigo == 0) {
	            nivelesJugador(exp);
	        } else {
	            nivelesJugador(exp - 15);
	        }

	        // Imprime una línea en blanco y muestra la barra de nivel
	        System.out.println();
	        System.out.print("Cantidad de puntos barra de nivel ");
	        interfazBarraNivel();
	        textoCambioUbicacion(); // Muestra el texto de cambio de ubicación
	    }
	}
	
	// Interacción con las salas
	public static void interactuacionSala() {
	    String usoFuente = ""; // Variable para almacenar la decisión de usar la fuente
	    String usoJefe = ""; // Variable para almacenar la decisión de enfrentar al jefe

	    // Verifica si el jugador está en una fuente
	    if (mapaCopia[filaActual][columnaActual].equals("Fuente")) {
	        // Ciclo que se ejecuta hasta que el jugador elija una opción válida
	        while (!usoFuente.equalsIgnoreCase("si") && !usoFuente.equalsIgnoreCase("no")) {
	            // Informa al jugador sobre la fuente y su vida actual
	            System.out.println(
	                    "\n\nUsted ha llegado a la fuente, quiere usarla? (Si/No) (Restaura la vida al completo), su cantidad de vida actual es de "
	                            + ROJO + vidaJugador + RESET);
	            usoFuente = input.nextLine(); // Lee la respuesta del jugador
	            usoFuente = usoFuente.toLowerCase(); // Convierte la respuesta a minúsculas

	            // Evalúa la opción seleccionada
	            switch (usoFuente) {
	                case "si":
	                    vidaJugador = maximoBarraJugador; // Restaura la vida del jugador
	                    System.out.print("\nSu vida se ha restaurado completamente, ahora dispone de " + ROJO + vidaJugador
	                            + RESET + " de vida");
	                    mapaCopia[filaActual][columnaActual] = "0"; // Marca la fuente como utilizada
	                    textoCambioUbicacion(); // Muestra el texto de cambio de ubicación
	                    break;
	                case "no":
	                    System.out.print("\nUsted " + ROJO + "NO" + RESET + " ha restaurado su vida");
	                    textoCambioUbicacion(); // Muestra el texto de cambio de ubicación
	                    break;
	                default:
	                    System.out.println("Valor inválido"); // Mensaje de error para opción no válida
	            }
	        }

	    // Verifica si el jugador está en la sala del jefe
	    } else if (mapaCopia[filaActual][columnaActual].equals("Jefe")) {
	        // Ciclo que se ejecuta hasta que el jugador elija una opción válida
	        while (!usoJefe.equalsIgnoreCase("luchar") && !usoJefe.equalsIgnoreCase("escapar")) {
	            // Informa al jugador sobre la situación en la sala del jefe
	            System.out.println("\n\nUsted ha llegado a la sala del Jefe, se ve bastante" + ROJO + " terrorrífico"
	                    + RESET
	                    + ", el demonio está devorando a otro aventurero, parece que lo está disfrutando bastante,"
	                    + " podrías llegar a escabullirte de la sala si eres lo suficientemente silencioso");
	            System.out.println(
	                    "¿quiere luchar contra él o prefiere escabullirse entre las sombras? (Luchar/Escapar) (Probabilidad de que te descubra mientras escapas del 10%)");
	            usoJefe = input.nextLine(); // Lee la respuesta del jugador
	            usoJefe = usoJefe.toLowerCase(); // Convierte la respuesta a minúsculas

	            // Evalúa la opción seleccionada
	            switch (usoJefe) {
	                case "luchar":
	                    System.out.println(AZUL + "\n\nLa batalla comienza" + RESET); // Informa sobre el inicio de la batalla
	                    pantallaBatalla(); // Llama al método de batalla
	                    mapaCopia[filaActual][columnaActual] = "0"; // Marca la sala del jefe como utilizada
	                    break;
	                case "escapar":
	                    int probabilidad = (int) (Math.random() * 10); // Genera una probabilidad aleatoria para escapar
	                    // Verifica si el jugador es descubierto
	                    if (probabilidad == 0) {
	                        System.out.println("\n\nEl " + ROJO + "Jefe " + RESET
	                                + "le ha descubierto, intente ser más sigiloso la próxima vez");
	                        System.out.println(AZUL + "La batalla comienza" + RESET); // Informa sobre el inicio de la batalla
	                        pantallaBatalla(); // Llama al método de batalla
	                        mapaCopia[filaActual][columnaActual] = "0"; // Marca la sala del jefe como utilizada
	                    } else {
	                        System.out.print("\n\nUsted ha escapado de la sala exitosamente"); // Informa que el escape fue exitoso
	                        textoCambioUbicacion(); // Muestra el texto de cambio de ubicación
	                    }
	                    break;
	                default:
	                    System.out.println("\nValor inválido"); // Mensaje de error para opción no válida
	            }
	        }
	    // Verifica si el jugador está en una sala con un enemigo
	    } else if (mapaCopia[filaActual][columnaActual].equals(mapa[filaActual][columnaActual])
	            && !mapaCopia[filaActual][columnaActual].equals("Inicio")) {
	        System.out.println(
	                ROJO + "\nCuidado!!" + RESET + " Hay un ser " + ROJO + "MUY malvado" + RESET + " en esta sala");
	        pantallaBatalla(); // Llama al método de batalla
	    } else {
	        // Verifica si el jugador está en la sala de inicio
	        if (mapaCopia[filaActual][columnaActual].equals("Inicio")) {
	            textoCambioUbicacion(); // Muestra el texto de cambio de ubicación
	        } else {
	            System.out.print("\n\nUsted ya ha interactuado con esta sala"); // Informa que la sala ya fue interactuada
	            textoCambioUbicacion(); // Muestra el texto de cambio de ubicación
	        }
	    }
	}
	
	// Movimiento del jugador por el mapa
	public static void movimientoMapa(String move) {
	    move = move.toLowerCase(); // Convierte el movimiento a minúsculas

	    switch (move) {
	        case "w": // Movimiento hacia arriba
	            if ((filaActual - 1) >= 0) { // Verifica que no salga del mapa
	                if (columnaActual < mapa[(filaActual - 1)].length) { // Verifica que la columna sea válida
	                    filaActual -= 1; // Actualiza la fila actual
	                    Localizacion = mapa[filaActual][columnaActual]; // Actualiza la localización
	                    System.out.print("\nSu ubicación ha cambiado correctamente, usted se sitúa ahora en "); // Mensaje de cambio de ubicación
	                    localizacionActual(); // Muestra la ubicación actual
	                    interactuacionSala(); // Llama al método para interactuar con la sala
	                } else {
	                    System.out.print("\nUbicación inválida, el mapa no posee esa columna"); // Mensaje de ubicación inválida
	                    textoCambioUbicacion(); // Llama al método para cambiar de ubicación
	                }
	            } else {
	                System.out.print("\nUbicación inválida, el mapa no posee esa fila"); // Mensaje de ubicación inválida
	                textoCambioUbicacion(); // Llama al método para cambiar de ubicación
	            }
	            break;

	        case "a": // Movimiento hacia la izquierda
	            if ((columnaActual - 1) >= 0) { // Verifica que no salga del mapa
	                columnaActual -= 1; // Actualiza la columna actual
	                Localizacion = mapa[filaActual][columnaActual]; // Actualiza la localización
	                System.out.print("\nSu ubicación ha cambiado correctamente, usted se sitúa ahora en "); // Mensaje de cambio de ubicación
	                localizacionActual(); // Muestra la ubicación actual
	                interactuacionSala(); // Llama al método para interactuar con la sala
	            } else {
	                System.out.print("\nUbicación inválida, el mapa no posee esa columna"); // Mensaje de ubicación inválida
	                textoCambioUbicacion(); // Llama al método para cambiar de ubicación
	            }
	            break;

	        case "s": // Movimiento hacia abajo
	            if ((filaActual + 1) < mapa.length) { // Verifica que no salga del mapa
	                if (columnaActual < mapa[(filaActual + 1)].length) { // Verifica que la columna sea válida
	                    filaActual += 1; // Actualiza la fila actual
	                    Localizacion = mapa[filaActual][columnaActual]; // Actualiza la localización
	                    System.out.print("\nSu ubicación ha cambiado correctamente, usted se sitúa ahora en "); // Mensaje de cambio de ubicación
	                    localizacionActual(); // Muestra la ubicación actual
	                    interactuacionSala(); // Llama al método para interactuar con la sala
	                } else {
	                    System.out.print("\nUbicación inválida, el mapa no posee esa columna"); // Mensaje de ubicación inválida
	                    textoCambioUbicacion(); // Llama al método para cambiar de ubicación
	                }
	            } else {
	                System.out.print("\nUbicación inválida, el mapa no posee esa fila"); // Mensaje de ubicación inválida
	                textoCambioUbicacion(); // Llama al método para cambiar de ubicación
	            }
	            break;

	        case "d": // Movimiento hacia la derecha
	            if ((columnaActual + 1) < mapa[filaActual].length) { // Verifica que no salga del mapa
	                columnaActual += 1; // Actualiza la columna actual
	                Localizacion = mapa[filaActual][columnaActual]; // Actualiza la localización
	                System.out.print("\nSu ubicación ha cambiado correctamente, usted se sitúa ahora en "); // Mensaje de cambio de ubicación
	                localizacionActual(); // Muestra la ubicación actual
	                interactuacionSala(); // Llama al método para interactuar con la sala
	            } else {
	                System.out.print("\nUbicación inválida, el mapa no posee esa columna"); // Mensaje de ubicación inválida
	                textoCambioUbicacion(); // Llama al método para cambiar de ubicación
	            }
	            break;

	        default: // Si la entrada no es válida
	            System.out.print("\nEntrada inválida"); // Mensaje de entrada inválida
	            textoCambioUbicacion(); // Llama al método para cambiar de ubicación
	    }
	}

	// Texto/Iniciación cambio de ubicación
	public static void textoCambioUbicacion() {
	    salidaMapa(mapa); // Muestra el mapa actual
	    System.out.println("\nSu vida actual es de " + ROJO + vidaJugador + RESET + " puntos sobre " + ROJO + maximoBarraJugador + RESET); // Muestra la vida del jugador
	    System.out.print("\nUsted se sitúa en "); // Indica la ubicación actual
	    localizacionActual(); // Muestra la ubicación actual
	    System.out.println(", usa WASD para moverte por el mapa"); // Instrucciones de movimiento
	    String movimiento = input.nextLine(); // Lee el movimiento del jugador
	    movimientoMapa(movimiento); // Llama al método de movimiento
	}

	// Iniciación y bucle del juego
	public static void juegoPrincipal() {
	    CambiarNombre(); // Cambia el nombre del jugador
	    while (vidaJugador > 0) { // Bucle que se ejecuta mientras el jugador tenga vida
	        textoCambioUbicacion(); // Llama al método para cambiar de ubicación
	        vidaJugador -= 100; // Reduce la vida del jugador
	    }
	}

	// Método principal
	public static void main(String[] args) {
	    menuInicio(); // Llama al menú de inicio
	}
}