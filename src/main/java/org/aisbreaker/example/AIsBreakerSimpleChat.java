package org.aisbreaker.example;

import org.aisbreaker.api.AIsBreaker;
import org.aisbreaker.api.AIsError;
import org.aisbreaker.api.AIsService;
import org.aisbreaker.api.AIsServiceProps;
import org.aisbreaker.api.model.Auth;
import org.aisbreaker.api.model.Input;
import org.aisbreaker.api.model.InputText;
import org.aisbreaker.api.model.Request;
import org.aisbreaker.api.model.ResponseFinal;

/**
 * AIsBreakerSimpleChat
 * 
 * A simple chat example using AIsBreaker Java API.
 */
public class AIsBreakerSimpleChat {
    public static void main(String[] args) {
        System.out.println("AIsBreakerSimpleChat");
        System.out.println("--------------------");
            
        try {
            // service configuration: select the service/serviceId you want to use
            AIsServiceProps serviceProps = new AIsServiceProps()
                .setServiceId("chat:dummy")
            
                //.setServiceId("chat:openai.com")
            
                //.setServiceId("chat:gemini.vertexai.google.com")
                //.setProject("<YOUR-GOOGLE-CLOUD-PROJECT>")          // optional for gemini.vertexai.google.com
                //.setLocation("<YOUR-GOOGLE-CLOUD-LOCATION>")        // optional for gemini.vertexai.google.com, e.g. "us-central1"
            
                //.setServiceId("chat:huggingface.co/<HF-ACCOUNT>/<HF-MODEL>")
                // e.g.:
                //.setServiceId("chat:huggingface.co/microsoft/DialoGPT-large")
            
                //.setServiceId("aisbreaker:mirror")
                //.setForward2ServiceProps(new AIsServiceProps()
                //    .setServiceId("chat:echo")
                //)
            ;
            
            // service initialization
            String aisbreakerServerURL =  null;//"https://api.demo.aisbreaker.org/";
            Auth auth = new Auth()
                // optionally, set your OpenAI API key:
                //.setSecret("sk-...")
            
                // optionally, set your Google Cloud (Vertext AI) API key:
                //.setSecret("googlecloud-account-json-base64_..")
            
                // optionally, set your Huggingface API key:
                //.setSecret("hf_...")
            
                // optionally, set your AIsBreaker API key:
                //.setSecret("aisbreaker_...")
            ;
            AIsService aisService =
                AIsBreaker.getAIsService(aisbreakerServerURL, serviceProps, auth); 

            
            // 1st question/prompt
            String question1 = "What is NodeJS?";
            System.out.println("***** Question1 *****\n"+question1+"\n");
            
            // 1st answer
            ResponseFinal response1 = aisService.process(new Request()
                .addInput(new Input()
                    .setText(new InputText()
                        .setRole("user")
                        .setContent(question1)
                    )
                )
            );
            System.out.println("***** Answer1 *****\n"+response1.getOutputs().get(0).getText().getContent()+"\n");
            
            
            // 2nd question/prompt
            String question2 = "shorter please";
            System.out.println("***** Question2 *****\n"+question2+"\n");
            
            // 2nd answer
            ResponseFinal response2 = aisService.process(new Request()
                .addInput(new Input()
                    .setText(new InputText()
                        .setRole("user")
                        .setContent(question2)
                    )
                )
                .setConversationState(response1.getConversationState())
            );
            System.out.println("***** Answer2 *****\n"+response2.getOutputs().get(0).getText().getContent()+"\n");

        } catch (AIsError e) {
            e.printStackTrace();
        }
    }
}
