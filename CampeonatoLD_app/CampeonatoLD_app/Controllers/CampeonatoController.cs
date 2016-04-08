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

        public FileResult DownloadFutebolLD()
        {
            byte[] fileBytes = System.IO.File.ReadAllBytes(Server.MapPath("/apk/futebolld.apk"));
            string fileName = "futebolld.apk";
            return File(fileBytes, System.Net.Mime.MediaTypeNames.Application.Octet, fileName);
        }
    }
}
