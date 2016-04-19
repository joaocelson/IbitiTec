using CampeonatoLD_app.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Security;
using System.Security.Cryptography.X509Certificates;
using System.Text;
using System.Web;
using System.Web.Mvc;

namespace CampeonatoLD_app.Controllers
{
    public class CampeonatoController : Controller
    {
        //
        // GET: /Campeonato/
        public ActionResult Index()
        {
            return View();
        }

        //
        // GET: /Campeonato/Details/5
        public ActionResult Details(int id)
        {
            return View();
        }

        //
        // GET: /Campeonato/Create
        public ActionResult Create()
        {
            return View();
        }

        //
        // POST: /Campeonato/Create
        [HttpPost]
        public ActionResult Create(FormCollection collection)
        {
            try
            {
                // TODO: Add insert logic here

                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }

        //
        // GET: /Campeonato/Edit/5
        public ActionResult Edit(int id)
        {
            return View();
        }

        //
        // POST: /Campeonato/Edit/5
        [HttpPost]
        public ActionResult Edit(int id, FormCollection collection)
        {
            try
            {
                // TODO: Add update logic here

                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }

        //
        // GET: /Campeonato/Delete/5
        public ActionResult Delete(int id)
        {
            return View();
        }

        //
        // POST: /Campeonato/Delete/5
        [HttpPost]
        public ActionResult Delete(int id, FormCollection collection)
        {
            try
            {
                // TODO: Add delete logic here

                return RedirectToAction("Index");
            }
            catch
            {
                return View();
            }
        }

        //TABELA CAMPEONATO PRIMEIRA DIVISÃO

        //
        // GET: /Campeonato/getPDTabela
        public String getPDTabela()
        {
            try
            {
                String line = String.Empty;
                ICollection<Rodada> rodadas = new List<Rodada>();
                using (StreamReader CsvReader = new StreamReader(Server.MapPath("/docs/pdTabela.csv")))
                {
                    while ((line = CsvReader.ReadLine()) != null)
                    {
                        Rodada rodada = new Rodada();
                        string[] vars = line.Split(',');
                        rodada.Numero = vars[0];
                        rodada.Data = vars[1];
                        rodada.Campo = vars[2];
                        rodada.HoraJogo1 = vars[3];
                        rodada.Jogo1 = vars[4];
                        rodadas.Add(rodada);
                    }
                    CsvReader.Close();
                }
                return JsonConvert.SerializeObject(rodadas, Formatting.Indented);
            }
            catch (Exception ex)
            {
                ex = ex;
                return null;
            }
        }

        //TABELA CAMPEONATO SEGUNDA DIVISÃO

        //
        // GET: /Campeonato/getPDTabela
        public String getSDTabela()
        {
            try
            {
                String line = String.Empty;
                ICollection<Rodada> rodadas = new List<Rodada>();
                using (StreamReader CsvReader = new StreamReader(Server.MapPath("/docs/sdTabela.csv")))
                {
                    while ((line = CsvReader.ReadLine()) != null)
                    {
                        Rodada rodada = new Rodada();
                        string[] vars = line.Split(',');
                        rodada.Numero = vars[0];
                        rodada.Data = vars[1];
                        rodada.Campo = vars[2];
                        rodada.HoraJogo1 = vars[3];
                        rodada.Jogo1 = vars[4];
                        rodada.HoraJogo2 = vars[5];
                        rodada.Jogo2 = vars[6];
                        rodadas.Add(rodada);
                    }
                    CsvReader.Close();
                }
                return JsonConvert.SerializeObject(rodadas, Formatting.Indented);
            }
            catch (Exception ex)
            {
                ex = ex;
                return null;
            }
        }


        //CLASSIFICACAO SEGUNDA DIVISÃO

        //
        // GET: /Campeonato/getPDTabela
        public String getSDClassificacao()
        {
            try
            {
                String line = String.Empty;
                ICollection<Classificacao> classificacaoList = new List<Classificacao>();
                using (StreamReader CsvReader = new StreamReader(Server.MapPath("/docs/sdClassificacao.csv")))
                {
                    while ((line = CsvReader.ReadLine()) != null)
                    {
                        Classificacao classificacao = new Classificacao();
                        string[] vars = line.Split(',');
                        classificacao.Posicao = vars[0];
                        classificacao.Time = vars[1];
                        classificacao.Pontos = vars[2];
                        classificacaoList.Add(classificacao);
                    }
                    CsvReader.Close();
                }
                return JsonConvert.SerializeObject(classificacaoList, Formatting.Indented);
            }
            catch (Exception ex)
            {
                ex = ex;
                return null;
            }
        }

        //CLASSIFICACAO PRIMEIRA DIVISÃO

        //
        // GET: /Campeonato/getPDTabela
        public String getPDClassificacao()
        {
            try
            {
                String line = String.Empty;
                ICollection<Classificacao> classificacaoList = new List<Classificacao>();
                using (StreamReader CsvReader = new StreamReader(Server.MapPath("/docs/pdClassificacao.csv")))
                {
                    while ((line = CsvReader.ReadLine()) != null)
                    {
                        Classificacao classificacao = new Classificacao();
                        string[] vars = line.Split(',');
                        classificacao.Posicao = vars[0];
                        classificacao.Time = vars[1];
                        classificacao.Pontos = vars[2];
                        classificacao.Jogos = vars[3];
                        classificacao.Vitorias = vars[4];
                        classificacao.Derrotas = vars[5];
                        classificacao.Empate = vars[6];
                        classificacao.PontosPP = vars[7];
                        classificacao.PontosPC = vars[8];

                        classificacaoList.Add(classificacao);
                    }
                    CsvReader.Close();
                }
                return JsonConvert.SerializeObject(classificacaoList, Formatting.Indented);
            }
            catch (Exception ex)
            {
                ex = ex;
                return null;
            }
        }

        //ARTILHARIA PRIMEIRA DIVISÃO

        //
        // GET: /Campeonato/getPDTabela
        public String getPDArtilharia()
        {
            try
            {
                String line = String.Empty;
                ICollection<Artilharia> artilhariaList = new List<Artilharia>();
                using (StreamReader CsvReader = new StreamReader(Server.MapPath("/docs/pdArtilharia.csv")))
                {
                    while ((line = CsvReader.ReadLine()) != null)
                    {
                        Artilharia artilharia = new Artilharia();
                        string[] vars = line.Split(',');
                        artilharia.Gols = vars[0];
                        artilharia.Nome = vars[1];
                        artilharia.Time = vars[2];
                        artilhariaList.Add(artilharia);
                    }
                    CsvReader.Close();
                }
                return JsonConvert.SerializeObject(artilhariaList, Formatting.Indented);
            }
            catch (Exception ex)
            {
                ex = ex;
                return null;
            }
        }

        //ARTILHARIA PRIMEIRA DIVISÃO

        //
        // GET: /Campeonato/getPDTabela
        public String getSDArtilharia()
        {
            try
            {
                String line = String.Empty;
                ICollection<Artilharia> artilhariaList = new List<Artilharia>();
                using (StreamReader CsvReader = new StreamReader(Server.MapPath("/docs/sdArtilharia.csv")))
                {
                    while ((line = CsvReader.ReadLine()) != null)
                    {
                        Artilharia artilharia = new Artilharia();
                        string[] vars = line.Split(',');
                        artilharia.Gols = vars[0];
                        artilharia.Nome = vars[1];
                        artilharia.Time = vars[2];
                        artilhariaList.Add(artilharia);
                    }
                    CsvReader.Close();
                }
                return JsonConvert.SerializeObject(artilhariaList, Formatting.Indented);
            }
            catch (Exception ex)
            {
                ex = ex;
                return null;
            }
        }

        //GETIMAGENS
        public ActionResult Image(string nomeImagem)
        {
            var dir = Server.MapPath("/Content/imagens/escudo");
            var path = Path.Combine(dir, nomeImagem);
            return base.File(path, "image/jpg");
        }

        public FileResult DownloadAPK()
        {
            byte[] fileBytes = System.IO.File.ReadAllBytes(Server.MapPath("/apk/futebolld.apk"));
            string fileName = "apk.apk";
            return File(fileBytes, System.Net.Mime.MediaTypeNames.Application.Octet, fileName);
        }

        //GOOGLE CLOUD MESSAGE  -- SEND MESSAGE FOR SERVER

        // POST: /Campeonato/Create
        public String EnviarMensagemGoogleCloud()
        {
            try
            {

            
            string deviceId = "f0tnwv5yu5w:APA91bH9NbXK-FeCvDw1gBSnq_sKNRxrv20iPEF1p6aC_zZ4z3LSFF7Fv5KY1UQGiL-f4LO954FQWbooOQL_rJFJ8FcvNlnIy9yItmb4Yp-sX-EAVMb2dBAuk_-JO2Vf73S0V3GxIxOp";

            string message = "Atualizado os resultados da última rodada";
            string tickerText = "Atualização Classificação Campeonato";
            string contentTitle = "LiFFA - Atualização Resultados e Classificação";

            string text = System.IO.File.ReadAllText((Server.MapPath("/docs/tokens.txt")));

            string[] tokens = text.Split('|');
            string response="";

            foreach (string str in tokens)
            {
                if (!str.Equals("")) { 
                deviceId = str;
                string postData =
                "{ \"registration_ids\": [ \"" + deviceId + "\" ], " +
                  "\"data\": {\"tickerText\":\"" + tickerText + "\", " +
                             "\"contentTitle\":\"" + contentTitle + "\", " +
                             "\"message\": \"" + message + "\"}}";

                //            string response = SendGCMNotification("AIzaSyCo_YCF3pzU6VL8e8quJxmnQZBAMyfvzkk", postData);
                response = SendNotification(deviceId, postData);
                }
            }
            return response;
            }
            catch (Exception ex)
            {
                return "";
            }
        }

        public string SendNotification(string deviceId, string message)
        {
            string SERVER_API_KEY = "AIzaSyAidNF4G4Ecr3ma7E_e-Wnp4d-okoFv5YI";
            var SENDER_ID = deviceId; //"application number";
            var value = message;
            WebRequest tRequest;
            tRequest = WebRequest.Create("https://android.googleapis.com/gcm/send");
            tRequest.Method = "post";
            tRequest.ContentType = " application/x-www-form-urlencoded;charset=UTF-8";
            tRequest.Headers.Add(string.Format("Authorization: key={0}", SERVER_API_KEY));

            tRequest.Headers.Add(string.Format("Sender: id={0}", SENDER_ID));

            string postData = "collapse_key=score_update&time_to_live=108&delay_while_idle=1&data.message=" + value + "&data.time=" + System.DateTime.Now.ToString() + "&registration_id=" + deviceId + "";
            Console.WriteLine(postData);
            Byte[] byteArray = Encoding.UTF8.GetBytes(postData);
            tRequest.ContentLength = byteArray.Length;

            Stream dataStream = tRequest.GetRequestStream();
            dataStream.Write(byteArray, 0, byteArray.Length);
            dataStream.Close();

            WebResponse tResponse = tRequest.GetResponse();

            dataStream = tResponse.GetResponseStream();

            StreamReader tReader = new StreamReader(dataStream);

            String sResponseFromServer = tReader.ReadToEnd();

            tReader.Close();
            dataStream.Close();
            tResponse.Close();
            return sResponseFromServer;
        }

        public static bool ValidateServerCertificate(object sender, X509Certificate certificate, X509Chain chain, SslPolicyErrors sslPolicyErrors)
        {
            return true;
        }

        // POST: /Campeonato/Create
        public String SendToken(string token)
        {
            try
            {

                //Pass the filepath and filename to the StreamWriter Constructor
                StreamWriter sw = new StreamWriter(Server.MapPath("/docs/tokens.txt"));

                //Write a line of text
                sw.Write(token + "|");

                //Close the file
                sw.Close();
            }
            catch (Exception e)
            {
                // Console.WriteLine("Exception: " + e.Message);
                return "";
            }
            finally
            {
                Console.WriteLine("Executing finally block.");
            }
            string ok = "OK";
            return ok;
        }

    }
}
