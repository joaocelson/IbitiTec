using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace LDApp.Models
{
    public class Telefone
    {
        [Required]
        [Key]
        public Guid TelefoneId { get; set; }
        
        public String Numero { get; set; }
        public String Descricao { get; set; }       
        public Guid ComercianteID { get; set; }

        [JsonIgnore]
        public virtual Comerciante Comerciante { get; set; }



    }
}