/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.allus.utils;

import com.co.allus.gestor.documental.GestorDocumental;
import java.util.ArrayList;

/**
 *
 * @author luis.cuervo
 */
public class TipoCteGdeOk {

    private transient GestorDocumental gestorDoc = new GestorDocumental();

//    ("01010",//cliente.getEstado_opt(),
//    "ODA",//, // quemado ODA
//    "S",//cliente.getTiene_otp(), // Quemado en S                                                                        
//    enrolado.getTipo_mecanismo(), //EML o SMS
//    "1",//cliente.getOtp_servicio(), // QUEMADO
//    opcion1==1?"N":opcion1==0?"S":"S", 
//    EstadoClave);
    public String RetornarTipoClienteGde(final String estadoClaveD, final String tipoClaveD, final String tieneClaveD, final String tipoOda, final String otpEstadoServicio, final String leyAntitramites, final String EstadoClave) {
        gestorDoc.imprimir("Arranca Tipo Cliente Gestiontel  --->");

        String Respuesta = "0";
        gestorDoc.imprimir("estadoClaveD ---> " + estadoClaveD);
        gestorDoc.imprimir("tipoClaveD ---> " + tipoClaveD);
        gestorDoc.imprimir("tieneClaveD ---> " + tieneClaveD);
        gestorDoc.imprimir("tipoOda ---> " + tipoOda);
        gestorDoc.imprimir("otpEstadoServicio ---> " + otpEstadoServicio);
        gestorDoc.imprimir("leyAntitramites ---> " + leyAntitramites);
        gestorDoc.imprimir("estadoClave ---> " + EstadoClave);


        //Cliente no tiene segunda clave
        if (EstadoClave.equals("NOEP")) {
            // Cliente en Ley Antitramites 
            if (leyAntitramites.equals("S")) {
                Respuesta = "5G";
            } else if (tieneClaveD.equals("S")) {
                if (tipoClaveD.equals("STK")) {

                    if (otpEstadoServicio.equals("1")) {

                        if (estadoClaveD.equals("01010")) // Activo
                        {
                            Respuesta = "1B";
                        }
                        if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                        {
                            Respuesta = "2D";
                        }
                        if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                        {
                            Respuesta = "3E";
                        }
                        if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                        {
                            Respuesta = "4F";
                        }
                        if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                        {
                            Respuesta = "2C";
                        }
                        if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                        {
                            Respuesta = "1A";
                        }


                    } else {
                        Respuesta = "6H"; //  OTP No Disponible 
                    }
                } else if (tipoClaveD.equals("ODA")) {

                    if (tipoOda.equals("SMS")) {

                        if (otpEstadoServicio.equals("1")) {

                            if (estadoClaveD.equals("01010")) // Activo
                            {
                                Respuesta = "8L";
                            }
                            if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                            {
                                Respuesta = "9O";
                            }
                            if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                            {
                                Respuesta = "2R";
                            }
                            if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                            {
                                Respuesta = "4T";
                            }
                            if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                            {
                                Respuesta = "9M";
                            }
                            if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                            {
                                Respuesta = "7I";
                            }

                        } else {
                            Respuesta = "8X";
                        }
                    } else if (tipoOda.equals("EML")) {

                        if (otpEstadoServicio.equals("1")) {

                            if (estadoClaveD.equals("01010")) // Activo
                            {
                                Respuesta = "7K";
                            }
                            if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                            {
                                Respuesta = "1Q";
                            }
                            if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                            {
                                Respuesta = "3S";
                            }
                            if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                            {
                                Respuesta = "5U";
                            }
                            if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                            {
                                Respuesta = "9N";
                            }
                            if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                            {
                                Respuesta = "7J";
                            }

                        } else {
                            Respuesta = "9Y";
                        }
                    }
                }
            } else {
                Respuesta = "B"; //Cliente no tiene segunda clave
            }
        }



        //Cliente con segunda clave bloqueo Voluntario   	
        // if (EstadoClave.equals("BLQA"))
        //	 Respuesta = "C";


        //Cliente con segunda clave bloqueo Voluntario   	
        if (EstadoClave.equals("BLQA")) {
            if (leyAntitramites.equals("S")) {
                Respuesta = "6G";
            } else if (tieneClaveD.equals("S")) {
                if (tipoClaveD.equals("STK")) {

                    if (otpEstadoServicio.equals("1")) {

                        if (estadoClaveD.equals("01010")) // Activo
                        {
                            Respuesta = "2B";
                        }
                        if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                        {
                            Respuesta = "3D";
                        }
                        if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                        {
                            Respuesta = "4E";
                        }
                        if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                        {
                            Respuesta = "5F";
                        }
                        if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                        {
                            Respuesta = "3C";
                        }
                        if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                        {
                            Respuesta = "2A";
                        }


                    } else {
                        Respuesta = "7H"; //  OTP No Disponible 
                    }
                } else if (tipoClaveD.equals("ODA")) {

                    if (tipoOda.equals("SMS")) {

                        if (otpEstadoServicio.equals("1")) {

                            if (estadoClaveD.equals("01010")) // Activo
                            {
                                Respuesta = "9L";
                            }
                            if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                            {
                                Respuesta = "1P";
                            }
                            if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                            {
                                Respuesta = "3R";
                            }
                            if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                            {
                                Respuesta = "5T";
                            }
                            if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                            {
                                Respuesta = "1N";
                            }
                            if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                            {
                                Respuesta = "8I";
                            }

                        } else {
                            Respuesta = "9X";
                        }
                    } else if (tipoOda.equals("EML")) {

                        if (otpEstadoServicio.equals("1")) {

                            if (estadoClaveD.equals("01010")) // Activo
                            {
                                Respuesta = "8K";
                            }
                            if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                            {
                                Respuesta = "2Q";
                            }
                            if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                            {
                                Respuesta = "4S";
                            }
                            if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                            {
                                Respuesta = "6U";
                            }
                            if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                            {
                                Respuesta = "1O";
                            }
                            if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                            {
                                Respuesta = "8J";
                            }

                        } else {
                            Respuesta = "1Z";
                        }
                    }
                }
            } else {
                Respuesta = "C"; //Cliente con segunda clave bloqueo Voluntario 
            }
        }





        //Cliente con segunda clave  bloqueo de preguntas de validacion	
        //if (EstadoClave.equals("BAUO") || EstadoClave.equals("BLQO") || EstadoClave.equals("SOLO") || EstadoClave.equals("ACTO") )
        // Respuesta = "D"; 


        //Cliente con segunda clave  bloqueo de preguntas de validacion	
        if (EstadoClave.equals("BAUO") || EstadoClave.equals("BLQO") || EstadoClave.equals("SOLO") || EstadoClave.equals("ACTO")) {
            if (leyAntitramites.equals("S")) {
                Respuesta = "7G";
            } else if (tieneClaveD.equals("S")) {
                if (tipoClaveD.equals("STK")) {

                    if (otpEstadoServicio.equals("1")) {

                        if (estadoClaveD.equals("01010")) // Activo
                        {
                            Respuesta = "3B";
                        }
                        if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                        {
                            Respuesta = "4D";
                        }
                        if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                        {
                            Respuesta = "5E";
                        }
                        if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                        {
                            Respuesta = "6F";
                        }
                        if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                        {
                            Respuesta = "4C";
                        }
                        if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                        {
                            Respuesta = "3A";
                        }


                    } else {
                        Respuesta = "8H"; //  OTP No Disponible 
                    }
                } else if (tipoClaveD.equals("ODA")) {

                    if (tipoOda.equals("SMS")) {

                        if (otpEstadoServicio.equals("1")) {

                            if (estadoClaveD.equals("01010")) // Activo
                            {
                                Respuesta = "1M";
                            }
                            if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                            {
                                Respuesta = "2P";
                            }
                            if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                            {
                                Respuesta = "4R";
                            }
                            if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                            {
                                Respuesta = "6T";
                            }
                            if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                            {
                                Respuesta = "2N";
                            }
                            if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                            {
                                Respuesta = "9I";
                            }

                        } else {
                            Respuesta = "1Y";
                        }
                    } else if (tipoOda.equals("EML")) {

                        if (otpEstadoServicio.equals("1")) {

                            if (estadoClaveD.equals("01010")) // Activo
                            {
                                Respuesta = "9K";
                            }
                            if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                            {
                                Respuesta = "3Q";
                            }
                            if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                            {
                                Respuesta = "5S";
                            }
                            if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                            {
                                Respuesta = "7U";
                            }
                            if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                            {
                                Respuesta = "2O";
                            }
                            if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                            {
                                Respuesta = "9J";
                            }

                        } else {
                            Respuesta = "2Z";
                        }
                    }
                }
            } else {
                Respuesta = "D"; //Cliente con segunda clave  bloqueo de preguntas de validacion 
            }
        }





        //Cliente con segunda clave bloqueada por intentos fallidos  
        //  if (EstadoClave.equals("BAUA"))
        //	 Respuesta = "E";


        //Cliente con segunda clave bloqueada por intentos fallidos  
        if (EstadoClave.equals("BAUA") || EstadoClave.equals("420") || EstadoClave.equals("90")) {
            if (leyAntitramites.equals("S")) {
                Respuesta = "8G";
            } else if (tieneClaveD.equals("S")) {
                if (tipoClaveD.equals("STK")) {

                    if (otpEstadoServicio.equals("1")) {

                        if (estadoClaveD.equals("01010")) // Activo
                        {
                            Respuesta = "4B";
                        }
                        if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                        {
                            Respuesta = "5D";
                        }
                        if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                        {
                            Respuesta = "6E";
                        }
                        if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                        {
                            Respuesta = "7F";
                        }
                        if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                        {
                            Respuesta = "5C";
                        }
                        if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                        {
                            Respuesta = "4A";
                        }


                    } else {
                        Respuesta = "9H"; //  OTP No Disponible 
                    }
                } else if (tipoClaveD.equals("ODA")) {

                    if (tipoOda.equals("SMS")) {

                        if (otpEstadoServicio.equals("1")) {

                            if (estadoClaveD.equals("01010")) // Activo
                            {
                                Respuesta = "2M";
                            }
                            if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                            {
                                Respuesta = "3P";
                            }
                            if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                            {
                                Respuesta = "5R";
                            }
                            if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                            {
                                Respuesta = "7T";
                            }
                            if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                            {
                                Respuesta = "3N";
                            }
                            if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                            {
                                Respuesta = "1J";
                            }

                        } else {
                            Respuesta = "2Y";
                        }
                    } else if (tipoOda.equals("EML")) {

                        if (otpEstadoServicio.equals("1")) {

                            if (estadoClaveD.equals("01010")) // Activo
                            {
                                Respuesta = "1L";
                            }
                            if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                            {
                                Respuesta = "4Q";
                            }
                            if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                            {
                                Respuesta = "6S";
                            }
                            if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                            {
                                Respuesta = "8U";
                            }
                            if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                            {
                                Respuesta = "3O";
                            }
                            if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                            {
                                Respuesta = "1K";
                            }

                        } else {
                            Respuesta = "3Z";
                        }
                    }
                }
            } else {
                Respuesta = "E";  //Cliente con segunda clave bloqueada por intentos fallidos  
            }
        }




        //Cliente con segunda clave pendiente de activar por primera vez     
        //if (EstadoClave.equals("SOLP"))
        // Respuesta = "F";

        //Cliente con segunda clave pendiente de activar  	
        //if (EstadoClave.equals("BLQP") || EstadoClave.equals("BAUP") || EstadoClave.equals("ACTP") )
        // Respuesta = "F";




        //Cliente con segunda clave pendiente de activar por primera vez     
        if (EstadoClave.equals("SOLP") || EstadoClave.equals("BLQP") || EstadoClave.equals("BAUP") || EstadoClave.equals("ACTP")) {

            if (leyAntitramites.equals("S")) {
                Respuesta = "9G";
            } else if (tieneClaveD.equals("S")) {
                if (tipoClaveD.equals("STK")) {

                    if (otpEstadoServicio.equals("1")) {

                        if (estadoClaveD.equals("01010")) // Activo
                        {
                            Respuesta = "5B";
                        }
                        if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                        {
                            Respuesta = "6D";
                        }
                        if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                        {
                            Respuesta = "7E";
                        }
                        if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                        {
                            Respuesta = "8F";
                        }
                        if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                        {
                            Respuesta = "6C";
                        }
                        if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                        {
                            Respuesta = "5A";
                        }


                    } else {
                        Respuesta = "1I"; //  OTP No Disponible 
                    }
                } else if (tipoClaveD.equals("ODA")) {

                    if (tipoOda.equals("SMS")) {

                        if (otpEstadoServicio.equals("1")) {

                            if (estadoClaveD.equals("01010")) // Activo
                            {
                                Respuesta = "3M";
                            }
                            if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                            {
                                Respuesta = "4P";
                            }
                            if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                            {
                                Respuesta = "6R";
                            }
                            if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                            {
                                Respuesta = "8T";
                            }
                            if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                            {
                                Respuesta = "4N";
                            }
                            if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                            {
                                Respuesta = "2J";
                            }

                        } else {
                            Respuesta = "3Y";
                        }
                    } else if (tipoOda.equals("EML")) {

                        if (otpEstadoServicio.equals("1")) {

                            if (estadoClaveD.equals("01010")) // Activo
                            {
                                Respuesta = "2L";
                            }
                            if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                            {
                                Respuesta = "5Q";
                            }
                            if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                            {
                                Respuesta = "7S";
                            }
                            if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                            {
                                Respuesta = "9U";
                            }
                            if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                            {
                                Respuesta = "4O";
                            }
                            if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                            {
                                Respuesta = "2K";
                            }

                        } else {
                            Respuesta = "4Z";
                        }
                    }
                }
            } else {
                Respuesta = "F";   //Cliente con segunda clave pendiente de activar por primera vez    
            }
        }






        //Cliente con segunda clave vencida    
        //  if (EstadoClave.equals("EXPP"))
        //	 Respuesta = "G";

        //Cliente con segunda clave vencida    
        if (EstadoClave.equals("EXPP")) {
            if (leyAntitramites.equals("S")) {
                Respuesta = "1H";
            } else if (tieneClaveD.equals("S")) {
                if (tipoClaveD.equals("STK")) {

                    if (otpEstadoServicio.equals("1")) {

                        if (estadoClaveD.equals("01010")) // Activo
                        {
                            Respuesta = "6B";
                        }
                        if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                        {
                            Respuesta = "7D";
                        }
                        if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                        {
                            Respuesta = "8E";
                        }
                        if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                        {
                            Respuesta = "9F";
                        }
                        if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                        {
                            Respuesta = "7C";
                        }
                        if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                        {
                            Respuesta = "6A";
                        }


                    } else {
                        Respuesta = "2I"; //  OTP No Disponible 
                    }
                } else if (tipoClaveD.equals("ODA")) {

                    if (tipoOda.equals("SMS")) {

                        if (otpEstadoServicio.equals("1")) {

                            if (estadoClaveD.equals("01010")) // Activo
                            {
                                Respuesta = "4M";
                            }
                            if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                            {
                                Respuesta = "5P";
                            }
                            if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                            {
                                Respuesta = "7R";
                            }
                            if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                            {
                                Respuesta = "9T";
                            }
                            if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                            {
                                Respuesta = "5N";
                            }
                            if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                            {
                                Respuesta = "3J";
                            }

                        } else {
                            Respuesta = "4Y";
                        }
                    } else if (tipoOda.equals("EML")) {

                        if (otpEstadoServicio.equals("1")) {

                            if (estadoClaveD.equals("01010")) // Activo
                            {
                                Respuesta = "3L";
                            }
                            if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                            {
                                Respuesta = "6Q";
                            }
                            if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                            {
                                Respuesta = "8S";
                            }
                            if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                            {
                                Respuesta = "1V";
                            }
                            if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                            {
                                Respuesta = "5O";
                            }
                            if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                            {
                                Respuesta = "3K";
                            }

                        } else {
                            Respuesta = "5Z";
                        }
                    }
                }
            } else {
                Respuesta = "G";    //Cliente con segunda clave vencida 
            }
        }



        //Cliente con segunda clave activa      
        if (EstadoClave.equals("ACTA")) {

            if (leyAntitramites.equals("S")) {
                Respuesta = "2H";
            } else if (tieneClaveD.equals("S")) {
                if (tipoClaveD.equals("STK")) {

                    if (otpEstadoServicio.equals("1")) {

                        if (estadoClaveD.equals("01010")) // Activo
                        {
                            Respuesta = "7B";
                        }
                        if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                        {
                            Respuesta = "8D";
                        }
                        if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                        {
                            Respuesta = "9E";
                        }
                        if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                        {
                            Respuesta = "1G";
                        }
                        if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                        {
                            Respuesta = "8C";
                        }
                        if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                        {
                            Respuesta = "7A";
                        }


                    } else {
                        Respuesta = "3I"; //  OTP No Disponible 
                    }
                } else if (tipoClaveD.equals("ODA")) {

                    if (tipoOda.equals("SMS")) {

                        if (otpEstadoServicio.equals("1")) {

                            if (estadoClaveD.equals("01010")) // Activo
                            {
                                Respuesta = "5M";
                            }
                            if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                            {
                                Respuesta = "6P";
                            }
                            if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                            {
                                Respuesta = "8R";
                            }
                            if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                            {
                                Respuesta = "1U";
                            }
                            if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                            {
                                Respuesta = "6N";
                            }
                            if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                            {
                                Respuesta = "4J";
                            }

                        } else {
                            Respuesta = "5Y";
                        }
                    } else if (tipoOda.equals("EML")) {

                        if (otpEstadoServicio.equals("1")) {

                            if (estadoClaveD.equals("01010")) // Activo
                            {
                                Respuesta = "4L";
                            }
                            if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                            {
                                Respuesta = "7Q";
                            }
                            if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                            {
                                Respuesta = "9S";
                            }
                            if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                            {
                                Respuesta = "2V";
                            }
                            if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                            {
                                Respuesta = "6O";
                            }
                            if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                            {
                                Respuesta = "4K";
                            }

                        } else {
                            Respuesta = "6Z";
                        }
                    }
                }
            } else {
                Respuesta = "H"; //Cliente con segunda clave activa }
            }
        }








        //Cliente logueado con ambas claves      
        if (EstadoClave.equals("LogueoAmbas")) {
            if (leyAntitramites.equals("S")) {
                Respuesta = "4H";
            } else {
                Respuesta = "J";
            }
        }




        //Cliente Con clave ppal , segunda clave invalida     
        if (EstadoClave.equals("418") || EstadoClave.equals("419")) {
            if (leyAntitramites.equals("S")) {
                Respuesta = "3H";
            } else {
                Respuesta = "I";
            }
        }



        //Cliente Con clave ppal , segunda clave bloqueada por intentos fallidos     
        // if (EstadoClave.equals("BAUA") || EstadoClave.equals("420") ||  EstadoClave.equals("90"))
        //	 Respuesta = "O";

        //Cliente Con clave ppal , fallo la validacion de segunda clave     
        if (EstadoClave.equals("failVal2da")) {
            Respuesta = "L";
        }

        //Cliente Con Clave Ppal, 2Clave Bloq. por Seguridad
        //if (EstadoClave.equals("SGBS"))
        // Respuesta = "S";


        //Cliente Con Clave Ppal, 2Clave Bloq. por Seguridad
        if (EstadoClave.equals("SGBS")) {
            if (leyAntitramites.equals("S")) {
                Respuesta = "5H";
            } else if (tieneClaveD.equals("S")) {
                if (tipoClaveD.equals("STK")) {

                    if (otpEstadoServicio.equals("1")) {

                        if (estadoClaveD.equals("01010")) // Activo
                        {
                            Respuesta = "1C";
                        }
                        if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                        {
                            Respuesta = "2E";
                        }
                        if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                        {
                            Respuesta = "3F";
                        }
                        if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                        {
                            Respuesta = "4G";
                        }
                        if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                        {
                            Respuesta = "1D";
                        }
                        if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                        {
                            Respuesta = "9A";
                        }


                    } else {
                        Respuesta = "6I"; //  OTP No Disponible 
                    }
                } else if (tipoClaveD.equals("ODA")) {

                    if (tipoOda.equals("SMS")) {

                        if (otpEstadoServicio.equals("1")) {

                            if (estadoClaveD.equals("01010")) // Activo
                            {
                                Respuesta = "8M";
                            }
                            if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                            {
                                Respuesta = "9P";
                            }
                            if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                            {
                                Respuesta = "2S";
                            }
                            if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                            {
                                Respuesta = "4U";
                            }
                            if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                            {
                                Respuesta = "8N";
                            }
                            if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                            {
                                Respuesta = "6J";
                            }

                        } else {
                            Respuesta = "8Y";
                        }
                    } else if (tipoOda.equals("EML")) {

                        if (otpEstadoServicio.equals("1")) {

                            if (estadoClaveD.equals("01010")) // Activo
                            {
                                Respuesta = "7L";
                            }
                            if (estadoClaveD.equals("01013")) // Bloqueo por intentos fallidos
                            {
                                Respuesta = "1R";
                            }
                            if (estadoClaveD.equals("00014")) // Bloqueo por seguridad
                            {
                                Respuesta = "3T";
                            }
                            if (estadoClaveD.equals("00010") || estadoClaveD.equals("00011") || estadoClaveD.equals("00013")) // Bloqueo voluntario
                            {
                                Respuesta = "5V";
                            }
                            if (estadoClaveD.equals("802")) // Clave Dinamica No Valida
                            {
                                Respuesta = "8O";
                            }
                            if (estadoClaveD.equals("OTP_VALIDO")) // OTP Valido
                            {
                                Respuesta = "6K";
                            }

                        } else {
                            Respuesta = "9Z";
                        }
                    }
                }
            } else {
                Respuesta = "S"; //Cliente Con Clave Ppal, 2Clave Bloq. por Seguridad
            }
        }


        gestorDoc.imprimir("Respuesta Para Gestiontel " + Respuesta);



        return Respuesta;
    }
}
