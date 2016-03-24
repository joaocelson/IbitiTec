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
                List<Comerciante> comerciantes = (List<Comerciante>) db.Comerciantes.ToList();
                
                foreach(Comerciante comerciante in comerciantes){
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
                List<Comerciante> comerciantes = (List<Comerciante>)db.Comerciantes.Where(a => ( a.TipoComercio.Descricao.Equals("POUSADA") && a.TipoComercio.Descricao.Equals("CHALE")) && a.Enderecos.FirstOrDefault().Bairro.Equals("IBITIPOCA")).ToList();

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
                List<Comerciante> comerciantes = (List<Comerciante>)db.Comerciantes.Where(a => a.TipoComercio.Descricao.Equals("RESTAURANTE") || a.Enderecos.FirstOrDefault().Bairro.Equals("IBITIPOCA")).ToList();

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
        public ActionResult Edit([Bind(Include="id,Nome,NomeFoto")] Comerciante comerciante)
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
