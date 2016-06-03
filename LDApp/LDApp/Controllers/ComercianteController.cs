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
                Endereco enderecoBD =  db.Enderecos.Find(endereco.EnderecoId);

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
                        String descricacaoComercio =  vars[8];
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
    }
}
