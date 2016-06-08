using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using LDApp.Models;
using System.IO;
using Newtonsoft.Json;
using System.Text;
using System.Security.Cryptography.X509Certificates;
using System.Net.Security;

namespace LDApp.Controllers
{
    public class ComercianteController : Controller
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: /Comerciante/
        public ActionResult Index()
        {
            return View(db.Comerciantes.ToList());
        }

        // GET: /Comerciante/
        public String GetComerciantes()
        {
            try
            {
                List<Comerciante> comerciantes = (List<Comerciante>)db.Comerciantes.ToList();

                foreach (Comerciante comerciante in comerciantes)
                {
                    comerciante.NomeFoto = @Url.Content("~/images/upload/") + comerciante.NomeFoto;

                }
                //Necessario converter o Json Serialize devido ao proxy do EntityFramework
                return JsonConvert.SerializeObject(comerciantes, Formatting.Indented);
            }
            catch (Exception ex)
            {
                ex = ex;
                return null;
            }

            //return View(db.Comerciantes.ToList());
        }

        // GET: /GetPousada/
        public String GetPousada()
        {
            try
            {
                List<Comerciante> comerciantes = (List<Comerciante>)db.Comerciantes.Where(a => (a.TipoComercio.Descricao.Equals("POUSADA") || a.TipoComercio.Descricao.Equals("CHALE")) && a.Enderecos.FirstOrDefault().Bairro.Equals("IBITIPOCA")).ToList();

                foreach (Comerciante comerciante in comerciantes)
                {
                    comerciante.NomeFoto = @Url.Content("~/images/upload/") + comerciante.NomeFoto;

                }
                //Necessario converter o Json Serialize devido ao proxy do EntityFramework
                return JsonConvert.SerializeObject(comerciantes, Formatting.Indented);
            }
            catch (Exception ex)
            {
                ex = ex;
                return null;
            }

            //return View(db.Comerciantes.ToList());
        }

        // GET: /GetEmpresasPublicidade/
        public String GetEmpresasPublicidade()
        {
            try
            {
                List<Comerciante> comerciantes = (List<Comerciante>)db.Comerciantes.Where(a => a.Publicidade != null).ToList();

                foreach (Comerciante comerciante in comerciantes)
                {
                    comerciante.NomeFoto = @Url.Content("~/images/upload/") + comerciante.NomeFoto;

                }
                //Necessario converter o Json Serialize devido ao proxy do EntityFramework
                return JsonConvert.SerializeObject(comerciantes, Formatting.Indented);
            }
            catch (Exception ex)
            {
                ex = ex;
                return null;
            }

            //return View(db.Comerciantes.ToList());
        }

        // GET: /GetRestaurantesIbitipoca/
        public String GetRestaurantesIbitipoca()
        {
            try
            {
                List<Comerciante> comerciantes = (List<Comerciante>)db.Comerciantes.Where(a => a.TipoComercio.Descricao.Equals("RESTAURANTE") && a.Enderecos.FirstOrDefault().Bairro.Equals("IBITIPOCA")).ToList();

                foreach (Comerciante comerciante in comerciantes)
                {
                    comerciante.NomeFoto = @Url.Content("~/images/upload/") + comerciante.NomeFoto;

                }
                //Necessario converter o Json Serialize devido ao proxy do EntityFramework
                return JsonConvert.SerializeObject(comerciantes, Formatting.Indented);
            }
            catch (Exception ex)
            {
                ex = ex;
                return null;
            }

            //return View(db.Comerciantes.ToList());
        }

        // GET: /GetRestaurantesIbitipoca/
        public String GetCasasIbitipoca()
        {
            try
            {
                List<Comerciante> comerciantes = (List<Comerciante>)db.Comerciantes.Where(a => a.TipoComercio.Descricao.Equals("CASA") && a.Enderecos.FirstOrDefault().Bairro.Equals("IBITIPOCA")).ToList();

                foreach (Comerciante comerciante in comerciantes)
                {
                    comerciante.NomeFoto = @Url.Content("~/images/upload/") + comerciante.NomeFoto;

                }
                //Necessario converter o Json Serialize devido ao proxy do EntityFramework
                return JsonConvert.SerializeObject(comerciantes, Formatting.Indented);
            }
            catch (Exception ex)
            {
                ex = ex;
                return null;
            }

            //return View(db.Comerciantes.ToList());
        }

        // GET: /GetRestaurantesIbitipoca/
        public String GetCasasFarmaciasPlantao()
        {
            try
            {
                String line = String.Empty;
                List<Comerciante> comerciantes = new List<Comerciante>();
                using (StreamReader CsvReader = new StreamReader(Server.MapPath("/docs/Farmacia_" + DateTime.Now.Month + ".csv")))
                {



                    while ((line = CsvReader.ReadLine()) != null)
                    {
                        Comerciante comerciante = new Comerciante();
                        string[] vars = line.Split(',');
                        comerciante.Nome = vars[0];
                        ICollection<Telefone> telefones = new List<Telefone>();
                        Telefone telefone = new Telefone();
                        telefone.Numero = vars[1];
                        telefones.Add(telefone);
                        comerciante.Telefones = telefones;
                        comerciantes.Add(comerciante);
                    }
                    CsvReader.Close();
                }
                return JsonConvert.SerializeObject(comerciantes, Formatting.Indented);
            }
            catch (Exception ex)
            {
                ex = ex;
                return null;
            }

            //return View(db.Comerciantes.ToList());
        }

        // POST: /Comerciante/
        public String EnviarCordenada(List<Endereco> enderecos)
        {
            try
            {
                Endereco endereco = enderecos[0];
                Endereco enderecoBD = db.Enderecos.Find(endereco.EnderecoId);

                enderecoBD.Latitude = endereco.Latitude;
                enderecoBD.Longitude = endereco.Longitude;

                db.SaveChanges();

                //Necessario converter o Json Serialize devido ao proxy do EntityFramework
                return "OK";
            }
            catch (Exception ex)
            {
                ex = ex;
                return "";
            }

            //return View(db.Comerciantes.ToList());
        }


        // GET: /Comerciante/Details/5
        public ActionResult Details(Guid? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Comerciante comerciante = db.Comerciantes.Find(id);
            if (comerciante == null)
            {
                return HttpNotFound();
            }
            return View(comerciante);
        }

        // GET: /Comerciante/Create
        public ActionResult Create()
        {
            Comerciante comercia = new Comerciante();
            comercia.TipoComercioList = new List<TipoComercio>();
            comercia.TipoComercioList = db.TipoComercios.ToList();
            ViewBag.Comerciante = comercia;
            return View(ViewBag.Comerciante);
        }

        // GET: /Comerciante/Create
        public String CreateFromArquivo()
        {
            try
            {
                String line = String.Empty;
                List<Comerciante> comerciantes = new List<Comerciante>();
                using (StreamReader CsvReader = new StreamReader(Server.MapPath("/docs/File.csv")))
                {

                    while ((line = CsvReader.ReadLine()) != null)
                    {
                        string[] vars = line.Split(',');
                        Comerciante comerciante = new Comerciante();
                        comerciante.ComercianteId = Guid.NewGuid();

                        ICollection<Telefone> telefones = new List<Telefone>();
                        Telefone telefone1 = new Telefone();
                        telefone1.TelefoneId = Guid.NewGuid();
                        Telefone telefone2 = new Telefone();
                        telefone2.TelefoneId = Guid.NewGuid();
                        Telefone telefone3 = new Telefone();
                        telefone3.TelefoneId = Guid.NewGuid();
                        Telefone telefone4 = new Telefone();
                        telefone4.TelefoneId = Guid.NewGuid();

                        comerciante.Nome = vars[0];
                        String descricacaoComercio = vars[8];
                        IEnumerable<TipoComercio> tipoComercios = db.TipoComercios.Where(a => a.Descricao == descricacaoComercio);
                        if (tipoComercios.Count() >= 1)
                        {
                            comerciante.TipoComercio = tipoComercios.First();
                        }
                        else
                        {
                            TipoComercio tpComercio = new TipoComercio();
                            tpComercio.TipoComercioId = Guid.NewGuid();
                            tpComercio.Descricao = vars[8];
                            db.TipoComercios.Add(tpComercio);
                            db.SaveChanges();
                            comerciante.TipoComercio = tpComercio;
                        }

                        db.Comerciantes.Add(comerciante);
                        db.SaveChanges();
                        telefone1.Comerciante = comerciante;
                        telefone2.Comerciante = comerciante;
                        telefone3.Comerciante = comerciante;
                        telefone4.Comerciante = comerciante;

                        if (!vars[1].Equals(""))
                        {
                            telefone1.Descricao = vars[1];
                            if (!vars[9].Equals(""))
                            {
                                telefone1.Descricao = vars[9];
                            }
                            telefones.Add(telefone1);
                            db.Telefones.Add(telefone1);
                            db.SaveChanges();
                        }
                        if (!vars[2].Equals(""))
                        {
                            telefone2.Numero = vars[2];
                            telefones.Add(telefone2);
                            db.Telefones.Add(telefone2);
                            db.SaveChanges();
                        }
                        if (!vars[3].Equals(""))
                        {
                            telefone3.Numero = vars[3];
                            telefones.Add(telefone3);

                            db.Telefones.Add(telefone3);
                            db.SaveChanges();
                        }
                        if (!vars[4].Equals(""))
                        {
                            telefone4.Numero = vars[4];
                            telefones.Add(telefone4);

                            db.Telefones.Add(telefone4);
                            db.SaveChanges();
                        }

                        comerciante.Telefones = telefones;

                        //ENDERECO
                        Endereco endereco = new Endereco();
                        endereco.Comerciante = comerciante;
                        endereco.EnderecoId = Guid.NewGuid();
                        endereco.Logradouro = vars[5];
                        endereco.Numero = vars[6];
                        endereco.Bairro = vars[7];
                        endereco.Cidade = "LIMA DUARTE";
                        endereco.Estado = "MG";
                        endereco.Longitude = vars[11];
                        endereco.Latitude = vars[10];
                        db.Enderecos.Add(endereco);
                        db.SaveChanges();


                    }
                    CsvReader.Close();
                }
                return "OK";
            }
            catch (Exception ex)
            {
                ex = ex;
                return null;
            }
        }


        // POST: /Comerciante/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "ComercianteId,Nome,NomeFoto, File, Telefones, Enderecos, TipoComercio")] Comerciante comerciante)
        {
            if (ModelState.IsValid)
            {
                HttpPostedFileBase file = comerciante.File;
                if (file != null)
                {
                    string pic = System.IO.Path.GetFileName(file.FileName);
                    string path = System.IO.Path.Combine(
                                           Server.MapPath("~/images/upload"), pic);
                    // file is uploaded
                    file.SaveAs(path);

                    // save the image path path to the database or you can send image
                    // directly to database
                    // in-case if you want to store byte[] ie. for DB
                    using (MemoryStream ms = new MemoryStream())
                    {
                        file.InputStream.CopyTo(ms);
                        byte[] array = ms.GetBuffer();
                    }

                }

                comerciante.TipoComercio = db.TipoComercios.Find(comerciante.TipoComercio.TipoComercioId);

                comerciante.NomeFoto = file.FileName;
                comerciante.ComercianteId = Guid.NewGuid();
                db.Comerciantes.Add(comerciante);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(comerciante);
        }

        public ActionResult GetNewTelefone()
        {
            var telefone = new Telefone
            {
                TelefoneId = Guid.NewGuid()
            };

            return PartialView("_PartialTelefone", telefone);
        }

        public ActionResult GetNewEndereco()
        {
            var endereco = new Endereco
            {
                EnderecoId = Guid.NewGuid()
            };

            return PartialView("_PartialEndereco", endereco);
        }

        // GET: /Comerciante/Edit/5
        public ActionResult Edit(Guid? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Comerciante comerciante = db.Comerciantes.Find(id);
            if (comerciante == null)
            {
                return HttpNotFound();
            }
            return View(comerciante);
        }

        // POST: /Comerciante/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "id,Nome,NomeFoto")] Comerciante comerciante)
        {
            if (ModelState.IsValid)
            {
                db.Entry(comerciante).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(comerciante);
        }

        // GET: /Comerciante/Delete/5
        public ActionResult Delete(Guid? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Comerciante comerciante = db.Comerciantes.Find(id);
            if (comerciante == null)
            {
                return HttpNotFound();
            }
            return View(comerciante);
        }

        // POST: /Comerciante/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(Guid id)
        {
            Comerciante comerciante = db.Comerciantes.Find(id);
            db.Comerciantes.Remove(comerciante);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        public ActionResult Image(string nomeImagem)
        {
            var dir = Server.MapPath("/images/upload");
            var path = Path.Combine(dir, nomeImagem);
            return base.File(path, "image/jpeg");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        //SEND TOKEN 
        //=======================================================================================

        //GOOGLE CLOUD MESSAGE  -- SEND MESSAGE FOR SERVER

        // POST: /Campeonato/Create
        public String EnviarMensagemGoogleCloud(String message, String tickerText, String contentTitle)
        {
            try
            {
                //string deviceId = "f0tnwv5yu5w:APA91bH9NbXK-FeCvDw1gBSnq_sKNRxrv20iPEF1p6aC_zZ4z3LSFF7Fv5KY1UQGiL-f4LO954FQWbooOQL_rJFJ8FcvNlnIy9yItmb4Yp-sX-EAVMb2dBAuk_-JO2Vf73S0V3GxIxOp";
                string deviceId;
                //string message = "Atualizado os resultados da última rodada";
                //string tickerText = "Atualização Classificação Campeonato";
                //string contentTitle = "FUTEBOL LD - Atualização Resultados e Classificação";
                String response = "";
                Token[] tokens = db.Tokens.ToArray();

                //string text = System.IO.File.ReadAllText((Server.MapPath("/docs/tokens.txt")));

                foreach (Token str in tokens)
                {
                    if (!str.TokenStr.Equals(""))
                    {
                        deviceId = str.TokenStr;
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
                return "NOK";
            }
        }

        public string SendNotification(string deviceId, string message)
        {
            try
            {
                string SERVER_API_KEY = "AIzaSyCWPM1kWJ-KYLlWEJouVW1-P2088UzwjdI";
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
            catch (Exception)
            {
                return "";
            }
        }

        public static bool ValidateServerCertificate(object sender, X509Certificate certificate, X509Chain chain, SslPolicyErrors sslPolicyErrors)
        {
            return true;
        }


        // POST: /Campeonato/Create
        public String SendToken(string tokenStr)
        {
            try
            {
                Token token = new Token();
                token.TokenStr = tokenStr;
                token.TokenId = new Guid();
                db.Tokens.Add(token);
                db.SaveChanges();
                return "OK";
            }
            catch (Exception e)
            {
                // Console.WriteLine("Exception: " + e.Message);
                return "";
            }
        }
    }
}
