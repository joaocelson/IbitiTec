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
    public class CampeonatoRepositorioADO : IRepositorio<Campeonatos>
    {
        private Contexto contexto;

        private void Inserir(Campeonatos Campeonato)
        {
            var strQuery = "";
            strQuery += " INSERT INTO Campeonato (nome, data_inicio) ";
            strQuery += string.Format(" VALUES ('{0}','{1}') ",
                Campeonato.Nome, Campeonato.DataInicio
                );
            using (contexto = new Contexto())
            {
                contexto.ExecutaComando(strQuery);
            }
        }

        private void Alterar(Campeonatos Campeonato)
        {
            var strQuery = "";
            strQuery += " UPDATE Campeonato SET ";
            strQuery += string.Format(" Nome = '{0}', ", Campeonato.Nome);
            strQuery += string.Format(" data_inicio = '{0}', ", Campeonato.DataInicio);
            strQuery += string.Format(" WHERE Id = {0} ", Campeonato.Id);
            using (contexto = new Contexto())
            {
                contexto.ExecutaComando(strQuery);
            }
        }

        public void Salvar(Campeonatos Campeonato)
        {
            if (Campeonato.Id > 0)
                Alterar(Campeonato);
            else
                Inserir(Campeonato);
        }

        public void Excluir(Campeonatos Campeonato)
        {
            using (contexto = new Contexto())
            {
                var strQuery = string.Format(" DELETE FROM Campeonato WHERE Id = {0}", Campeonato.Id);
                contexto.ExecutaComando(strQuery);
            }
        }

        public IEnumerable<Campeonatos> ListarTodos()
        {
            using (contexto = new Contexto())
            {
                var strQuery = " SELECT * FROM Campeonato ";
                var retornoDataReader = contexto.ExecutaComandoComRetorno(strQuery);
                return TransformaReaderEmListaDeObjeto(retornoDataReader);
            }
        }

        public Campeonatos ListarPorId(string id)
        {
            using (contexto = new Contexto())
            {
                var strQuery = string.Format(" SELECT * FROM Campeonato WHERE Id = {0} ", id);
                var retornoDataReader = contexto.ExecutaComandoComRetorno(strQuery);
                return TransformaReaderEmListaDeObjeto(retornoDataReader).FirstOrDefault();
            }
        }

        private List<Campeonatos> TransformaReaderEmListaDeObjeto(SqlDataReader reader)
        {
            var Campeonato = new List<Campeonatos>();
            while (reader.Read())
            {int idBolao = 0;
                if(!reader["id_bolao"].ToString().Equals("")){
                     idBolao = Convert.ToInt16(reader["id_bolao"].ToString());
                }
                    

                var temObjeto = new Campeonatos()
                {
                    Id = Convert.ToInt32(reader["id"].ToString()),
                    Nome = reader["Nome"].ToString(),
                    IdBola = idBolao,
                    DataInicio = DateTime.Parse(reader["data_inicio"].ToString())
                };
                Campeonato.Add(temObjeto);
            }
            reader.Close();
            return Campeonato;
        }

        public void AdicionarTimes(List<Time> times, string idCampeonato)
        {
            SqlTransaction transacao = null;
            try
            {
                using (contexto = new Contexto())
                {
                    transacao = contexto.MinhaConexao().BeginTransaction(IsolationLevel.ReadCommitted);

                    foreach (Time time in times)
                    {
                        var strQuery = "";
                        strQuery += " INSERT INTO time_campeonato (id_time, id_campeonato) ";
                        strQuery += string.Format(" VALUES ('{0}','{1}') ",
                            time.Id, idCampeonato);
                        contexto.ExecutaComandoTransacao(strQuery, transacao);
                        TratamentoLog.GravarLog("Times: " + time.Nome + " associado ao campeonato: " + idCampeonato);
                    }
                    transacao.Commit();
                }
            }
            catch (Exception ex)
            {
                transacao.Rollback();
                TratamentoLog.GravarLog("PartidaRepositorioADO::GerarPartidaAutomaticamenteTime:. Erro ao geras Partidas" + ex.Message, TratamentoLog.NivelLog.Erro);
            }
        }
    }
}
