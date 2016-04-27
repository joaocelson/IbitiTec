using Campeonato.Dominio;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GerenciadorCampeonato.Models
{
    public class Rodada
    {
        public String Numero { get; set; }
        public String Data { get; set; }
        public String Campo { get; set; }
        public String HoraJogo1 { get; set; }
        public String Jogo1 { get; set; }
        public String HoraJogo2 { get; set; }
        public String Jogo2 { get; set; }

        public List<Rodada> ConverterPartidasParaRodada(IEnumerable<Partida> partidas)
        {
            try
            {


                List<Rodada> listRodada = new List<Rodada>();
                Rodada rodada = new Rodada();
                int contatoRodada = 0;
                foreach (Partida objPartida in partidas)
                {
                    if (contatoRodada == 0)
                    {
                        rodada.Numero = objPartida.Rodada;
                        rodada.Campo = objPartida.LocalPartida;
                        rodada.Data = objPartida.DataPartida.ToString("dd/MM/yyyy");
                        rodada.HoraJogo1 = objPartida.DataPartida.ToString("HH:mm");
                        rodada.Jogo1 = objPartida.EscudoPequenoMandante.Split('.')[0] + " - " + objPartida.GolMandante + " X " + objPartida.GolVisitante + " - " + objPartida.EscudoPequenoVisitante.Split('.')[0];
                       
                    }
                    contatoRodada++;
                    if (contatoRodada == 2)
                    {
                        rodada.HoraJogo2 = objPartida.DataPartida.ToString("HH:mm");
                        rodada.Jogo2 = objPartida.EscudoPequenoMandante.Split('.')[0] + " - " + objPartida.GolMandante + " X " + objPartida.GolVisitante + " - " + objPartida.EscudoPequenoVisitante.Split('.')[0];

                        listRodada.Add(rodada);
                        contatoRodada = 0;
                        rodada = new Rodada();
                    }
                }

                return listRodada;
            }
            catch (Exception ex)
            {

                throw;
            }
        }
    }
}