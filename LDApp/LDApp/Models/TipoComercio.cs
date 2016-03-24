using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace LDApp.Models
{
    public class TipoComercio
    {
        [Key]
        [Required]
        public Guid TipoComercioId { get; set; }
        public String Descricao { get; set; }


    }
}