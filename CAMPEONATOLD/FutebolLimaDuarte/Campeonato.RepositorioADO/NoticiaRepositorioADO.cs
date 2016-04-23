using Campeonato.Dominio.contrato;
using Campeonato.Dominio;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
using Campeonato.Dominio.util;
using System.Data;

namespace Campeonato.RepositorioADO
{
    public class NoticiaRepositorioADO : IRepositorio<Noticia>
    {
        private Contexto contexto;

        private void Inserir(Noticia noticia)
        {
            var strQuery = "";
            strQuery += " INSERT INTO Campeonato (nome, data_inicio) ";
            strQuery += string.Format(" VALUES ('{0}','{1}') "
                //,Campeonato.Nome, Campeonato.DataInicio
                );
            using (contexto = new Contexto())
            {
                contexto.ExecutaComando(strQuery);
            }
        }

        private void Alterar(Noticia noticia)
        {
            var strQuery = "";
            strQuery += " UPDATE Campeonato SET ";
            strQuery += string.Format(" Nome = '{0}', ", noticia.FonteNoticia);
            strQuery += string.Format(" data_inicio = '{0}', ", noticia.TextoChamada);
            strQuery += string.Format(" WHERE Id = {0} ", noticia.Id);
            using (contexto = new Contexto())
            {
                contexto.ExecutaComando(strQuery);
            }
        }

        public void Salvar(Noticia noticia)
        {
            if (noticia.Id > 0)
                Alterar(noticia);
            else
                Inserir(noticia);
        }

        public void Excluir(Noticia noticia)
        {
            using (contexto = new Contexto())
            {
                var strQuery = string.Format(" DELETE FROM Campeonato WHERE Id = {0}", noticia.Id);
                contexto.ExecutaComando(strQuery);
            }
        }

        public IEnumerable<Noticia> ListarTodos()
        {
            using (contexto = new Contexto())
            {
                var strQuery = " SELECT * FROM noticia ";
                var retornoDataReader = contexto.ExecutaComandoComRetorno(strQuery);
                return TransformaReaderEmListaDeObjeto(retornoDataReader);
            }
        }

        public Noticia ListarPorId(string id)
        {
            using (contexto = new Contexto())
            {
                var strQuery = string.Format(" SELECT * FROM Campeonato WHERE Id = {0} ", id);
                var retornoDataReader = contexto.ExecutaComandoComRetorno(strQuery);
                return TransformaReaderEmListaDeObjeto(retornoDataReader).FirstOrDefault();
            }
        }

        private List<Noticia> TransformaReaderEmListaDeObjeto(SqlDataReader reader)
        {
            var noticia = new List<Noticia>();
            while (reader.Read())
            {
                    

                var temObjeto = new Noticia()
                {
                    Id = Convert.ToInt32(reader["id"].ToString()),
                    FonteNoticia= reader["Nome"].ToString(),
                    
                    DataNoticia = DateTime.Parse(reader["data_inicio"].ToString())
                };
                noticia.Add(temObjeto);
            }
            reader.Close();
            return noticia;
        }


        Noticia IRepositorio<Noticia>.ListarPorId(string id)
        {
            throw new NotImplementedException();
        }
    }
}
