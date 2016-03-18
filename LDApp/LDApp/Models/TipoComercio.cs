using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace LDApp.Models
{
    public class TipoComercio
    {
        public Guid TipoComercioId { get; set; }

        public String Descricao { get; set; }

        [JsonIgnore]
        public virtual Comerciante Comerciante { get; set; }
    }
}