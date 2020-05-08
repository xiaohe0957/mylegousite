import javax.lang.model.element.NestingKind;

public class demo {
    public static void main(String[] args) {
        String s1 = "abcdef";

        String s2 = "adefe";
contains(s1,s2);
    }
    public static String contains(String s1,String s2){
        for (int i = s2.length();i>0;i--){
            for (int j =0,z = i;z<s1.length();j++,z++){
                String s = s2.substring(j, z);
                if (s1.contains(s)){
                    System.out.println(s);
                    return s;
                }
            }
        }
        return null;
    }
}
