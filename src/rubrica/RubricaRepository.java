package rubrica;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RubricaRepository {

    private final List<Persona> elencoPersone = new ArrayList<>();
    private final File file;

    public RubricaRepository(File file){
        this.file = file;
    }

    public List<Persona> getElencoPersone() {
        return elencoPersone;
    }

    public void load(){
        elencoPersone.clear();
        if(!file.exists()) return;

        try(Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)){
            while(scanner.hasNextLine()){
                String line = scanner.nextLine().trim();
                if(line.isEmpty()) continue;

                String[] tokens = line.split(";",-1);
                if(tokens.length != 5) continue;

                String nome = tokens[0].trim();
                String cognome = tokens[1].trim();
                String indirizzo = tokens[2].trim();
                String telefono = tokens[3].trim();
                int eta;

                try{
                    eta = Integer.parseInt(tokens[4].trim());
                }catch(Exception e){
                    continue;
                }

                Persona persona = new Persona(nome,cognome,indirizzo,telefono,eta);
                elencoPersone.add(persona);
            }
        }catch(Exception e){

        }
    }

    public void save(){
        try{
            try(PrintStream out = new PrintStream(new FileOutputStream(file,false),true,StandardCharsets.UTF_8)){
                for(Persona persona:elencoPersone){
                    out.printf("%s;%s;%s;%s;%d%n",
                            checkString(persona.getNome()),
                            checkString(persona.getCognome()),
                            checkString(persona.getIndirizzo()),
                            checkString(persona.getTelefono()),
                            persona.getEta());
                }
            }catch(Exception e){

            }
        }catch(Exception e){

        }
    }

    private String checkString(String s){
        if(s == null) return "";
        return s.replace(";",",");
    }
}
