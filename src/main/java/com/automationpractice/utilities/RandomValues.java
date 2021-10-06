package com.automationpractice.utilities;

public class RandomValues {
        public static int randomNumber(int start, int end) {
            return (int) ((Math.random() * (end - start)) + start);
        }

        public static String randomNumAsString(int quant){
            final String alpha_numeric = "0123456789";
            StringBuilder builder = new StringBuilder();
            while (quant-- != 0){
                int character = (int)(Math.random()*alpha_numeric.length());
                builder.append(alpha_numeric.charAt(character));
            }
            return builder.toString();
        }
}
