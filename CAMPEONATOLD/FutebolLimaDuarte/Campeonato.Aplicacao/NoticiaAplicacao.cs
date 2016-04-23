using Campeonato.Dominio;
using Campeonato.Dominio.contrato;
using Campeonato.RepositorioADO;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Campeonato.Aplicacao
{
    public class NoticiaAplicacao
    {
        private readonly IRepositorio<Noticia> repositorio;

        public NoticiaAplicacao(IRepositorio<Noticia> repo)
        {
            repositorio = repo;
        }
    }
}
