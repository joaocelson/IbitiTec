using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(JcAssessoria.Startup))]
namespace JcAssessoria
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
