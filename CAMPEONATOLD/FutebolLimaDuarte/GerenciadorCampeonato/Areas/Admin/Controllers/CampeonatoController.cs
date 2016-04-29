using Campeonato.Aplicacao;
using Campeonato.Dominio;
using Campeonato.Aplicacao;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Routing;
using Campeonato.UI.WEB.Security;
using Newtonsoft.Json;

namespace Campeonato.UI.WEB.Areas.Admin
{
    public class CampeonatoController : Controller
    {
        //
        // GET: /Admin/Campeonato/

        private readonly CampeonatoAplicacao appCampeonato;
        private readonly TimeAplicacao appTimes;

        private readonly ClassificacaoAplicacao appClassificacao;

        public CampeonatoController()
        {
            appCampeonato = CampeonatoAplicacaoConstrutor.CampeonatoAplicacaoADO();
            appClassificacao = ClassificacaoAplicacaoConstrutor.ClassificacaoAplicacaoADO();

            appTimes = TimeAplicacaoConstrutor.TimeAplicacaoADO();
        }

        public ActionResult Index()
        {
            var listaDeCampeonatos = appCampeonato.ListarTodos();
            return View(listaDeCampeonatos);
        }

        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult Cadastrar()
        {
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult Cadastrar(Campeonatos campeonato)
        {
            if (ModelState.IsValid)
            {
                appCampeonato.Salvar(campeonato);
                return RedirectToAction("Index");
            }
            return View(campeonato);
        }


        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult SelecionarTimes(string id)
        {
            //var listaDeCampeonatos = appCampeonato.ListarTodos();
            TimeCampeonato timesCampeonato = new TimeCampeonato();
            timesCampeonato.Times = appTimes.ListarTodos();
            timesCampeonato.IdCampeonato = id;
            return View(timesCampeonato);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult SelecionarTimes(int id)
        {

            var listaTimes = appTimes.ListarTodos();
            var listaTimesAdicionar = new List<Time>();

            for (int i = 0; i < listaTimes.Count(); i++)
            {
                Time time = listaTimes.ToList<Time>()[i];

                for (int j = 0; j < Request.Form.Count; j++)
                {
                    if (Request.Form.Keys[j].Equals(time.Id.ToString()))
                    {
                        var checkbox = Request.Form[j];
                        if (checkbox != "false")
                        {
                            time.SelecionadoCampeonato = true;
                            listaTimesAdicionar.Add(time);
                        }
                    }
                }
            }
            //Arrumar - JOAO CELSON

            appCampeonato.AdicionarTimes(listaTimesAdicionar, id.ToString());
            return RedirectToAction("TimesPorCampeonato");
        }

        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult Editar(string id)
        {
            var campeonato = appCampeonato.ListarPorId(id);

            if (campeonato == null)
                return HttpNotFound();

            return View(campeonato);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult Editar(Campeonatos campeonato)
        {
            if (ModelState.IsValid)
            {
                appCampeonato.Salvar(campeonato);
                return RedirectToAction("Index");
            }
            return View(campeonato);
        }

        public ActionResult Detalhes(string id)
        {
            var campeonato = appCampeonato.ListarPorId(id);

            if (campeonato == null)
                return HttpNotFound();

            return View(campeonato);
        }

        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult Excluir(string id)
        {
            var campeonato = appCampeonato.ListarPorId(id);

            if (campeonato == null)
                return HttpNotFound();

            return View(campeonato);
        }

        [HttpPost, ActionName("Excluir")]
        [ValidateAntiForgeryToken]
        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult ExcluirConfirmado(string id)
        {
            var campeonato = appCampeonato.ListarPorId(id);
            appCampeonato.Excluir(campeonato);
            return RedirectToAction("Index");
        }

        public ActionResult TimesPorCampeonato(string idCampeonato)
        {
            TimeCampeonato timeCampeonato = new TimeCampeonato();
            timeCampeonato.Times = appTimes.ListarTimesCampeonato(idCampeonato);
            timeCampeonato.IdCampeonato = idCampeonato;
            return View(timeCampeonato);
        }

        public ActionResult Tabela(string id)
        {
            return RedirectToAction("Tabela", new RouteValueDictionary(
    new { controller = "Partida", action = "Tabela", Id = id }));

        }

        public ActionResult Classificacao(string id)
        {

            var listaClassificacao = appClassificacao.ListarClassicacaoPorCampeonato(id);
            return View(listaClassificacao);
        }

        public ActionResult CampeoesPorCampeonato(string id)
        {
            var listaCampeos = appClassificacao.ListarCampeoesPorCampeonato(id);
            return View(listaCampeos);
        }


        //JSON - Retorna todos os dados JSON
        //=======================================================

        public String ObterClassificacaoJson(string id)
        {
            var listaClassificacao = appClassificacao.ListarClassicacaoPorCampeonato(id);
            return JsonConvert.SerializeObject(listaClassificacao, Formatting.Indented);
        }

        public String ObterArtilhariaJson(string id)
        {
            var listaArtilheiros = appCampeonato.ArtilhariaPorCampeonato(id);
            return JsonConvert.SerializeObject(listaArtilheiros, Formatting.Indented);
        }
    }
}
