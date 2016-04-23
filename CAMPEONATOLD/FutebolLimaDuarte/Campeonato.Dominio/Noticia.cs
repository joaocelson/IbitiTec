using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Campeonato.Dominio
{
    public class Noticia
    {
        public int Id { get; set; }
        public string TituloChamada { get; set; }
        public string TextoChamada { get; set; }
        public string UrlFotoChamada { get; set; }
        public FotosVideos ListaFotosVideos { get; set; }
        public string TextoNoticia { get; set; }
        public string FonteNoticia { get; set; }
        public DateTime DataNoticia { get; set; }
        public string Titulo { get; set; }
    }
}
