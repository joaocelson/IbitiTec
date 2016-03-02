using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace LDApp.Models
{
    public class Endereco
    {
        [Required]
        [Key]
        public Guid EnderecoId { get; set; }

        public String Logradouro { get; set; }
        public String Numero { get; set; }
        public String Complemento { get; set; }
        public String Cidade { get; set; }
        public String Estado { get; set; }
        public String Bairro { get; set; }

        public Guid ComercianteId { get; set; }
        [JsonIgnore]
        public virtual Comerciante Comerciante { get; set; }
    }
}