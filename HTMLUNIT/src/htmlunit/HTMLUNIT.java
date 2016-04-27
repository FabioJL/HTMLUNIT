/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlunit;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.host.URL;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Fábio
 */
public class HTMLUNIT {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        // Obtém a página de login.
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        
        CookieManager cookieManager = webClient.getCookieManager();
        cookieManager.setCookiesEnabled(true);

        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        HtmlPage page = webClient.getPage("http://www.receita.fazenda.gov.br/Aplicacoes/ATCTA/cpf/ConsultaPublica.asp");
        HtmlForm formConsulta = page.getFormByName("frmConsultaPublica");
        
        // Obtém os elementos do formulário.
        HtmlTextInput inputCPF = formConsulta.querySelector("input[name='txtCPF']");
        HtmlTextInput inputDataNascimento = formConsulta.querySelector("input[name='txtDataNascimento']");
        HtmlButtonInput botaoEnviar = formConsulta.getInputByName("Enviar");
        // Define o valor do atributo 'value' dos inputs.
        inputCPF.setValueAttribute("08403048971");
        inputDataNascimento.setValueAttribute("10/11/1992");
        
        HtmlPage returnPage = botaoEnviar.click();
        
        System.out.println(returnPage.asXml());
        
        HtmlForm formCaptcha = returnPage.getFormByName("form_captcha");
        HtmlImage image = returnPage.<HtmlImage>getFirstByXPath("//img[@src='./captcha/gerarCaptcha.asp']");
        File imageFile = new File("D:/image.jpg");
        image.saveAs(imageFile);
        
        JOptionPane.showMessageDialog(null, "Blah blah blah", "About", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("D:\\image.jpg"));
        String text = JOptionPane.showInputDialog("Valor");
        
        HtmlTextInput inputCaptcha = formCaptcha.querySelector("input[name='txtTexto_captcha_serpro_gov_br']");
        inputCaptcha.setValueAttribute(text);
        
        //HtmlHiddenInput inputCPFtemo = formCaptcha.querySelector("input[name='tempTxtCPF']");
        //HtmlHiddenInput inputDataNascimentotemp = formCaptcha.querySelector("input[name='tempTxtNascimento']");
        
        //inputCPFtemo.setValueAttribute("08403048971");
        //inputDataNascimentotemp.setValueAttribute("10/11/1992");
        
        HtmlSubmitInput botaoOK = formCaptcha.getInputByValue("OK");
        HtmlPage paginaAposOLogin = botaoOK.click();
        
        // Simula o "click" no botão de submit e aguarda retorno
        //HtmlPage paginaAposOLogin = botaoEnviar.click();

        // Mostra o código html da página
        System.out.println(paginaAposOLogin.asText());
    }
    
}
