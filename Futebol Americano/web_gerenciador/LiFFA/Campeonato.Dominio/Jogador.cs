using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Campeonato.Dominio
{
    public class Jogador
    {
        public int Id { get; set; }
        public string Nome { get; set; }
        public string Foto { get; set; }
        public string Posicao { get; set; }
        public string Descricao { get; set; }
        public Time Time { get; set; }
        public Campeonatos Campeonato { get; set; }
        
    }
}

