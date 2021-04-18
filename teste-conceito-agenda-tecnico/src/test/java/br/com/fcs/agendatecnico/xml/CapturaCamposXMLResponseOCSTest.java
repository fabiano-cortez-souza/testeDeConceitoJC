package br.com.fcs.agendatecnico.xml;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.fcs.agendatecnico.xml.CapturaCamposXMLResponseOCS;

@RunWith(MockitoJUnitRunner.class)
class CapturaCamposXMLResponseOCSTest {

	private CapturaCamposXMLResponseOCS capturaCamposXMLResponseOCS;
	
	private Long valorCodeOk = Long.valueOf(0); 
    private Long valorCodeInvalido = Long.MAX_VALUE;
	
    private Long valorDedicatedAccountID220 = Long.valueOf(220);
    private Long valorDedicatedAccountIDAusente = Long.valueOf(0);
    private Long dedicatedAccountActiveValue1Ausente = Long.valueOf(0);

    private String stringResponseOcsXMLOk = "<methodResponse>" +
                                            "   <params>" +
                                            "      <param>" +
                                            "         <value>" +
                                            "            <struct>" +
                                            "               <member>" +
                                            "                  <name>accountFlagsAfter</name>" +
                                            "                  <value>" +
                                            "                     <struct>" +
                                            "                        <member>" +
                                            "                           <name>activationStatusFlag</name>" +
                                            "                           <value>" +
                                            "                              <boolean>1</boolean>" +
                                            "                           </value>" +
                                            "                        </member>" +
                                            "                        <member>" +
                                            "                           <name>negativeBarringStatusFlag</name>" +
                                            "                           <value>" +
                                            "                              <boolean>0</boolean>" +
                                            "                           </value>" +
                                            "                        </member>" +
                                            "                        <member>" +
                                            "                           <name>serviceFeePeriodExpiryFlag</name>" +
                                            "                           <value>" +
                                            "                              <boolean>0</boolean>" +
                                            "                           </value>" +
                                            "                        </member>" +
                                            "                        <member>" +
                                            "                           <name>serviceFeePeriodWarningActiveFlag</name>" +
                                            "                           <value>" +
                                            "                              <boolean>0</boolean>" +
                                            "                           </value>" +
                                            "                        </member>" +
                                            "                        <member>" +
                                            "                           <name>supervisionPeriodExpiryFlag</name>" +
                                            "                           <value>" +
                                            "                              <boolean>0</boolean>" +
                                            "                           </value>" +
                                            "                        </member>" +
                                            "                        <member>" +
                                            "                           <name>supervisionPeriodWarningActiveFlag</name>" +
                                            "                           <value>" +
                                            "                              <boolean>0</boolean>" +
                                            "                           </value>" +
                                            "                        </member>" +
                                            "                     </struct>" +
                                            "                  </value>" +
                                            "               </member>" +
                                            "               <member>" +
                                            "                  <name>accountFlagsBefore</name>" +
                                            "                  <value>" +
                                            "                     <struct>" +
                                            "                        <member>" +
                                            "                           <name>activationStatusFlag</name>" +
                                            "                           <value>" +
                                            "                              <boolean>1</boolean>" +
                                            "                           </value>" +
                                            "                        </member>" +
                                            "                        <member>" +
                                            "                           <name>negativeBarringStatusFlag</name>" +
                                            "                           <value>" +
                                            "                              <boolean>0</boolean>" +
                                            "                           </value>" +
                                            "                        </member>" +
                                            "                        <member>" +
                                            "                           <name>serviceFeePeriodExpiryFlag</name>" +
                                            "                           <value>" +
                                            "                              <boolean>0</boolean>" +
                                            "                           </value>" +
                                            "                        </member>" +
                                            "                        <member>" +
                                            "                           <name>serviceFeePeriodWarningActiveFlag</name>" +
                                            "                           <value>" +
                                            "                              <boolean>0</boolean>" +
                                            "                           </value>" +
                                            "                        </member>" +
                                            "                        <member>" +
                                            "                           <name>supervisionPeriodExpiryFlag</name>" +
                                            "                           <value>" +
                                            "                              <boolean>0</boolean>" +
                                            "                           </value>" +
                                            "                        </member>" +
                                            "                        <member>" +
                                            "                           <name>supervisionPeriodWarningActiveFlag</name>" +
                                            "                           <value>" +
                                            "                              <boolean>0</boolean>" +
                                            "                           </value>" +
                                            "                        </member>" +
                                            "                     </struct>" +
                                            "                  </value>" +
                                            "               </member>" +
                                            "               <member>" +
                                            "                  <name>accountValue1</name>" +
                                            "                  <value>" +
                                            "                     <string>1300</string>" +
                                            "                  </value>" +
                                            "               </member>" +
                                            "               <member>" +
                                            "                  <name>availableServerCapabilities</name>" +
                                            "                  <value>" +
                                            "                     <array>" +
                                            "                        <data>" +
                                            "                           <value>" +
                                            "                              <i4>805646916</i4>" +
                                            "                           </value>" +
                                            "                           <value>" +
                                            "                              <i4>1576</i4>" +
                                            "                           </value>" +
                                            "                        </data>" +
                                            "                     </array>" +
                                            "                  </value>" +
                                            "               </member>" +
                                            "               <member>" +
                                            "                  <name>currency1</name>" +
                                            "                  <value>" +
                                            "                     <string>BRL</string>" +
                                            "                  </value>" +
                                            "               </member>" +
                                            "               <member>" +
                                            "                  <name>dedicatedAccountChangeInformation</name>" +
                                            "                  <value>" +
                                            "                     <array>" +
                                            "                        <data>" +
                                            "                           <value>" +
                                            "                              <struct>" +
                                            "                                 <member>" +
                                            "                                    <name>dedicatedAccountActiveValue1</name>" +
                                            "                                    <value>" +
                                            "                                       <string>3000</string>" +
                                            "                                    </value>" +
                                            "                                 </member>" +
                                            "                                 <member>" +
                                            "                                    <name>dedicatedAccountID</name>" +
                                            "                                    <value>" +
                                            "                                       <i4>220</i4>" +
                                            "                                    </value>" +
                                            "                                 </member>" +
                                            "                                 <member>" +
                                            "                                    <name>dedicatedAccountUnitType</name>" +
                                            "                                    <value>" +
                                            "                                       <i4>1</i4>" +
                                            "                                    </value>" +
                                            "                                 </member>" +
                                            "                                 <member>" +
                                            "                                    <name>dedicatedAccountValue1</name>" +
                                            "                                    <value>" +
                                            "                                       <string>3000</string>" +
                                            "                                    </value>" +
                                            "                                 </member>" +
                                            "                              </struct>" +
                                            "                           </value>" +
                                            "                        </data>" +
                                            "                     </array>" +
                                            "                  </value>" +
                                            "               </member>" +
                                            "               <member>" +
                                            "                  <name>negotiatedCapabilities</name>" +
                                            "                  <value>" +
                                            "                     <array>" +
                                            "                        <data>" +
                                            "                           <value>" +
                                            "                              <i4>805646916</i4>" +  
                                            "                           </value>" +
                                            "                           <value>" +
                                            "                              <i4>1576</i4>" +
                                            "                           </value>" +
                                            "                        </data>" +
                                            "                     </array>" +
                                            "                  </value>" +
                                            "               </member>" +
                                            "               <member>" +
                                            "                  <name>originTransactionID</name>" +
                                            "                  <value>" +
                                            "                     <string>1</string>" +
                                            "                  </value>" +
                                            "               </member>" +
                                            "               <member>" +
                                            "                  <name>responseCode</name>" +
                                            "                  <value>" +
                                            "                     <i4>0</i4>" +
                                            "                  </value>" +
                                            "               </member>" +
                                            "            </struct>" +
                                            "         </value>" +
                                            "      </param>" +
                                            "   </params>" +
                                            "</methodResponse>" ;   
    
	String responseCodeRemove = "               <member>" +
                                "                  <name>responseCode</name>" +
                                "                  <value>" +
                                "                     <i4>0</i4>" +
                                "                  </value>" +
                                "               </member>" ;

	@BeforeEach
	public void setUp() {
		initMocks(this);
		capturaCamposXMLResponseOCS = new CapturaCamposXMLResponseOCS();
	}
	
    @Test
    void validaConversaoCamposOkXMLResponseOCS() { 
        Long valorDedicatedAccountActiveValue1Esperado = new Long(3000);
        
        capturaCamposXMLResponseOCS.capturaCamposXML(stringResponseOcsXMLOk.trim());

        assertEquals(valorCodeOk, capturaCamposXMLResponseOCS.getValorCode()); 
        assertEquals(valorDedicatedAccountID220, capturaCamposXMLResponseOCS.getValorDedicatedAccountID());
        assertEquals(valorDedicatedAccountActiveValue1Esperado,capturaCamposXMLResponseOCS.getValorDedicatedAccountActiveValue1());
    }

    
    
    @Test
    void validaConversaoBranco() { 
        capturaCamposXMLResponseOCS.capturaCamposXML("");
        assertEquals(valorCodeInvalido,capturaCamposXMLResponseOCS.getValorCode());
    }
    
    @Test
    void validaConversaoResponseCodeValorCodeAusente() {    	
        String stringResponseOcsXMLincompleto = stringResponseOcsXMLOk.replace(responseCodeRemove, "");
        capturaCamposXMLResponseOCS.capturaCamposXML(stringResponseOcsXMLincompleto);
        assertEquals(valorCodeInvalido,capturaCamposXMLResponseOCS.getValorCode());
    }
    
    @Test
    void validaConversaoResponseCodeValorCodeInvalido() { 
    	String responseCodeinvalido = "               <member>" +
                                      "                  <name>responseCode</name>" +
                                      "                  <value>" +
                                      "                  </value>" +
                                      "               </member>" ;    	
    	
        String stringResponseOcsXMLincompleto = stringResponseOcsXMLOk.replace(responseCodeRemove, responseCodeinvalido);
        capturaCamposXMLResponseOCS.capturaCamposXML(stringResponseOcsXMLincompleto);
        assertEquals(valorCodeInvalido,capturaCamposXMLResponseOCS.getValorCode());
    }  
    
    @Test
    void validaConversaoDedicatedAccountChangeInformationAusente() { 
    String dedicatedAccountChangeInformationRemove =  "               <member>" +
                                                      "                  <name>dedicatedAccountChangeInformation</name>" +
                                                      "                  <value>" +
                                                      "                     <array>" +
                                                      "                        <data>" +
                                                      "                           <value>" +
                                                      "                              <struct>" +
                                                      "                                 <member>" +
                                                      "                                    <name>dedicatedAccountActiveValue1</name>" +
                                                      "                                    <value>" +
                                                      "                                       <string>3000</string>" +
                                                      "                                    </value>" +
                                                      "                                 </member>" +
                                                      "                                 <member>" +
                                                      "                                    <name>dedicatedAccountID</name>" +
                                                      "                                    <value>" +
                                                      "                                       <i4>220</i4>" +
                                                      "                                    </value>" +
                                                      "                                 </member>" +
                                                      "                                 <member>" +
                                                      "                                    <name>dedicatedAccountUnitType</name>" +
                                                      "                                    <value>" +
                                                      "                                       <i4>1</i4>" +
                                                      "                                    </value>" +
                                                      "                                 </member>" +
                                                      "                                 <member>" +
                                                      "                                    <name>dedicatedAccountValue1</name>" +
                                                      "                                    <value>" +
                                                      "                                       <string>3000</string>" +
                                                      "                                    </value>" +
                                                      "                                 </member>" +
                                                      "                              </struct>" +
                                                      "                           </value>" +
                                                      "                        </data>" +
                                                      "                     </array>" +
                                                      "                  </value>" +
                                                      "               </member>";
    
    	String stringResponseOcsXMLincompleto = stringResponseOcsXMLOk.replace(dedicatedAccountChangeInformationRemove, "");
    	capturaCamposXMLResponseOCS.capturaCamposXML(stringResponseOcsXMLincompleto);
    	assertEquals(valorCodeOk,capturaCamposXMLResponseOCS.getValorCode());
        assertEquals(valorDedicatedAccountIDAusente, capturaCamposXMLResponseOCS.getValorDedicatedAccountID());
        assertEquals(dedicatedAccountActiveValue1Ausente, capturaCamposXMLResponseOCS.getValorDedicatedAccountActiveValue1());        
    }
}
