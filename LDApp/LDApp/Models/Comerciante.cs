using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace LDApp.Models
{
    public class Comerciante
    {
        public Guid ComercianteId { get; set; }
        
        public String Nome { get; set; }
        public virtual ICollection<Telefone> Telefones { get; set; }
        public virtual ICollection<Endereco> Enderecos{ get; set; }
        public String NomeFoto { get; set; }
        
        [NotMapped]
        public HttpPostedFileBase File { get; set; } 

       
    }
}