using CampeonatoLD_app.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
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
    }
}
