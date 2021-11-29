package validation;

import com.jfoenix.controls.JFXTextField;

import java.util.ArrayList;

public class Validate {

    public static Object validate(ArrayList<JFXTextField> fields, ArrayList<String> regex){
        for (int i=0;i<fields.size();i++){
            if (fields.get(i).getText().isEmpty()){
                System.out.println(i);

                return null;
            }else if(!fields.get(i).getText().matches(regex.get(i))){
                return fields.get(i).getText();
            }
        }
        return true;
    }
}
