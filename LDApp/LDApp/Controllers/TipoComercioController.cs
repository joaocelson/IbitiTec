using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using LDApp.Models;

namespace LDApp.Controllers
{
    public class TipoComercioController : Controller
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: /TipoComercio/
        public ActionResult Index()
        {
            return View(db.TipoComercios.ToList());
        }

        // GET: /TipoComercio/Details/5
        public ActionResult Details(Guid? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            TipoComercio tipocomercio = db.TipoComercios.Find(id);
            if (tipocomercio == null)
            {
                return HttpNotFound();
            }
            return View(tipocomercio);
        }

        // GET: /TipoComercio/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: /TipoComercio/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include="TipoComercioId,Descricao")] TipoComercio tipocomercio)
        {
            if (ModelState.IsValid)
            {
                tipocomercio.TipoComercioId = Guid.NewGuid();
                db.TipoComercios.Add(tipocomercio);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(tipocomercio);
        }

        // GET: /TipoComercio/Edit/5
        public ActionResult Edit(Guid? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            TipoComercio tipocomercio = db.TipoComercios.Find(id);
            if (tipocomercio == null)
            {
                return HttpNotFound();
            }
            return View(tipocomercio);
        }

        // POST: /TipoComercio/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include="TipoComercioId,Descricao")] TipoComercio tipocomercio)
        {
            if (ModelState.IsValid)
            {
                db.Entry(tipocomercio).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(tipocomercio);
        }

        // GET: /TipoComercio/Delete/5
        public ActionResult Delete(Guid? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            TipoComercio tipocomercio = db.TipoComercios.Find(id);
            if (tipocomercio == null)
            {
                return HttpNotFound();
            }
            return View(tipocomercio);
        }

        // POST: /TipoComercio/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(Guid id)
        {
            TipoComercio tipocomercio = db.TipoComercios.Find(id);
            db.TipoComercios.Remove(tipocomercio);
            db.SaveChanges();
            return RedirectToAction("Index");
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
