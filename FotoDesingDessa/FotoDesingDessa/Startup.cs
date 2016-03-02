using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(FotoDesingDessa.Startup))]
namespace FotoDesingDessa
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
