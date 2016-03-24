namespace LDApp.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class initialMigration : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.TipoComercios", "TipoComercioId", "dbo.Comerciantes");
            DropIndex("dbo.TipoComercios", new[] { "TipoComercioId" });
            AddColumn("dbo.Comerciantes", "TipoComercio_TipoComercioId", c => c.Guid());
            CreateIndex("dbo.Comerciantes", "TipoComercio_TipoComercioId");
            AddForeignKey("dbo.Comerciantes", "TipoComercio_TipoComercioId", "dbo.TipoComercios", "TipoComercioId");
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.Comerciantes", "TipoComercio_TipoComercioId", "dbo.TipoComercios");
            DropIndex("dbo.Comerciantes", new[] { "TipoComercio_TipoComercioId" });
            DropColumn("dbo.Comerciantes", "TipoComercio_TipoComercioId");
            CreateIndex("dbo.TipoComercios", "TipoComercioId");
            AddForeignKey("dbo.TipoComercios", "TipoComercioId", "dbo.Comerciantes", "ComercianteId");
        }
    }
}
