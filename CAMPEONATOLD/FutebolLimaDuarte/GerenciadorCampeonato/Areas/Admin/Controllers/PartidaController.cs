using Campeonato.Aplicacao;
using Campeonato.Dominio;
using Campeonato.UI.WEB.Security;
using GerenciadorCampeonato.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Routing;
using System.Web.UI.WebControls;

namespace Campeonato.UI.WEB.Areas.Admin
{
    public class PartidaController : Controller
    {
        private readonly PartidaAplicacao appPartida;
        private readonly CampeonatoAplicacao appCampeonato;
        private readonly TimeAplicacao appTime;
        private readonly JogadorAplicacao appJogador;
        public PartidaController()
        {
            appPartida = PartidaAplicacaoConstrutor.PartidaAplicacaoADO();
            appCampeonato = CampeonatoAplicacaoConstrutor.CampeonatoAplicacaoADO();
            appTime = TimeAplicacaoConstrutor.TimeAplicacaoADO();
            appJogador = JogadorAplicacaoConstrutor.JogadorAplicacaoADO();
        }


        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult Index()
        {
            var listaDePartida = appPartida.ListarTodos();
            var listaDeCampeonatos = appCampeonato.ListarTodos();
            ViewBag.DropDownValues = new SelectList(listaDeCampeonatos, "id", "Nome");
            return View(listaDePartida);
        }

        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult PaginaInicial()
        {
            return View();
        }

        [AcceptVerbs(HttpVerbs.Post)]
        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult GerarPartidas(FormCollection form)
        {

            string idCampeonatoSelecionado = form["MeusCampeonatos"];
            appPartida.GerarPartidasAutomaticamente(idCampeonatoSelecionado);
            return Redirect("Index");
        }

        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult Cadastrar()
        {
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult Cadastrar(Partida partida)
        {
            if (ModelState.IsValid)
            {
                appPartida.Salvar(partida);
                return RedirectToAction("Index");
            }
            return View(partida);
        }

        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult Editar(string id)
        {
            var partida = appPartida.ListarPorId(id);

            if (partida == null)
                return HttpNotFound();

            return View(partida);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult Editar(Partida partida)
        {
            //if (ModelState.IsValid)
            //{
            if (partida.InverterMandante)
            {
                appPartida.InverterManadate(ref partida);
            }
            appPartida.Salvar(partida);
            //return RedirectToAction("Index");
            //}
            return RedirectToAction("Index");
        }

        public ActionResult Detalhes(string id)
        {
            var partida = appPartida.ListarPorId(id);

            if (partida == null)
                return HttpNotFound();

            return View(partida);
        }

        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult Excluir(string id)
        {
            var Partida = appPartida.ListarPorId(id);

            if (Partida == null)
                return HttpNotFound();

            return View(Partida);
        }

        [HttpPost, ActionName("Excluir")]
        [ValidateAntiForgeryToken]
        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult ExcluirConfirmado(string id)
        {
            var partida = appPartida.ListarPorId(id);
            appPartida.Excluir(partida);
            return RedirectToAction("Index");
        }

        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult Resultado(string id)
        {
            var partida = appPartida.ListarPorId(id);
            partida.TimeMandanteObj = appTime.ListarPorId(partida.IdTimeMandante);
            partida.TimeVisitanteObj = appTime.ListarPorId(partida.IdTimeVisitante);

            //Descomentar para rodar com o tabela de jogadores.
            partida.TimeMandanteObj.ListaJogadores = appJogador.ListarPorTimeCampeonato(partida.IdCampeonato, partida.IdTimeMandante);
            partida.TimeVisitanteObj.ListaJogadores = appJogador.ListarPorTimeCampeonato(partida.IdCampeonato, partida.IdTimeVisitante);

            if (partida == null)
                return HttpNotFound();

            return View(partida);
        }

        public ActionResult TabelaJson(string id)
        {
            Rodada rodada = new Rodada();
            var partida = rodada.ConverterPartidasParaRodada(appPartida.ListaTabelaPorCampeonato(id));

            if (partida == null)
                return HttpNotFound();

            return View(partida);
        }

        public ActionResult Tabela(string id)
        {
            var partida = appPartida.ListaTabelaPorCampeonato(id);

            if (partida == null)
                return HttpNotFound();

            return View(partida);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult Resultado(Partida partida)
        {
            //if (ModelState.IsValid)
            //{
            appPartida.Resultado(partida);
            //}
            return RedirectToAction("Index");
        }

        public ActionResult ListarUltimaRodada()
        {
            var listaUltimaRodada = appPartida.ListarUltimaRodada();
            return View(listaUltimaRodada);
        }

        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult ComentarPartidaOnline(string id)
        {
            Partida partida = appPartida.ListarPorId(id);
            return View(partida);
        }

        public ActionResult Comentario(string id)
        {
            var comentario = appPartida.ComentarioPartida(id);
            return View(comentario);
        }

        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult Comentar(string id, string comentario)
        {
            appPartida.ComentarPartida(id, comentario);
            return RedirectToAction("ComentarPartidaOnline", new RouteValueDictionary(
    new { controller = "Partida", action = "ComentarPartidaOnline", id = id }));
        }

        [PermissoesFiltro(Roles = "Admin")]
        public ActionResult FotosVideos(string id)
        {
            var partida = appPartida.ListarPorId(id);
            return View(partida);
        }
    }
}
