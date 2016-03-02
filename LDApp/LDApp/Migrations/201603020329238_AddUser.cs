namespace LDApp.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class AddUser : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "dbo.Comerciantes",
                c => new
                    {
                        ComercianteId = c.Guid(nullable: false),
                        Nome = c.String(),
                        NomeFoto = c.String(),
                    })
                .PrimaryKey(t => t.ComercianteId);
            
            CreateTable(
                "dbo.Enderecoes",
                c => new
                    {
                        EnderecoId = c.Guid(nullable: false),
                        Logradouro = c.String(),
                        Numero = c.String(),
                        Complemento = c.String(),
                        Cidade = c.String(),
                        Estado = c.String(),
                        Bairro = c.String(),
                        ComercianteId = c.Guid(nullable: false),
                    })
                .PrimaryKey(t => t.EnderecoId)
                .ForeignKey("dbo.Comerciantes", t => t.ComercianteId, cascadeDelete: true)
                .Index(t => t.ComercianteId);
            
            CreateTable(
                "dbo.Telefones",
                c => new
                    {
                        TelefoneId = c.Guid(nullable: false),
                        Numero = c.String(),
                        Descricao = c.String(),
                        ComercianteID = c.Guid(nullable: false),
                    })
                .PrimaryKey(t => t.TelefoneId)
                .ForeignKey("dbo.Comerciantes", t => t.ComercianteID, cascadeDelete: true)
                .Index(t => t.ComercianteID);
            
            CreateTable(
                "dbo.AspNetRoles",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        Name = c.String(nullable: false),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.AspNetUsers",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128),
                        UserName = c.String(),
                        PasswordHash = c.String(),
                        SecurityStamp = c.String(),
                        Discriminator = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => t.Id);
            
            CreateTable(
                "dbo.AspNetUserClaims",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        ClaimType = c.String(),
                        ClaimValue = c.String(),
                        User_Id = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => t.Id)
                .ForeignKey("dbo.AspNetUsers", t => t.User_Id, cascadeDelete: true)
                .Index(t => t.User_Id);
            
            CreateTable(
                "dbo.AspNetUserLogins",
                c => new
                    {
                        UserId = c.String(nullable: false, maxLength: 128),
                        LoginProvider = c.String(nullable: false, maxLength: 128),
                        ProviderKey = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => new { t.UserId, t.LoginProvider, t.ProviderKey })
                .ForeignKey("dbo.AspNetUsers", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId);
            
            CreateTable(
                "dbo.AspNetUserRoles",
                c => new
                    {
                        UserId = c.String(nullable: false, maxLength: 128),
                        RoleId = c.String(nullable: false, maxLength: 128),
                    })
                .PrimaryKey(t => new { t.UserId, t.RoleId })
                .ForeignKey("dbo.AspNetRoles", t => t.RoleId, cascadeDelete: true)
                .ForeignKey("dbo.AspNetUsers", t => t.UserId, cascadeDelete: true)
                .Index(t => t.RoleId)
                .Index(t => t.UserId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.AspNetUserClaims", "User_Id", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserRoles", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.AspNetUserRoles", "RoleId", "dbo.AspNetRoles");
            DropForeignKey("dbo.AspNetUserLogins", "UserId", "dbo.AspNetUsers");
            DropForeignKey("dbo.Telefones", "ComercianteID", "dbo.Comerciantes");
            DropForeignKey("dbo.Enderecoes", "ComercianteId", "dbo.Comerciantes");
            DropIndex("dbo.AspNetUserClaims", new[] { "User_Id" });
            DropIndex("dbo.AspNetUserRoles", new[] { "UserId" });
            DropIndex("dbo.AspNetUserRoles", new[] { "RoleId" });
            DropIndex("dbo.AspNetUserLogins", new[] { "UserId" });
            DropIndex("dbo.Telefones", new[] { "ComercianteID" });
            DropIndex("dbo.Enderecoes", new[] { "ComercianteId" });
            DropTable("dbo.AspNetUserRoles");
            DropTable("dbo.AspNetUserLogins");
            DropTable("dbo.AspNetUserClaims");
            DropTable("dbo.AspNetUsers");
            DropTable("dbo.AspNetRoles");
            DropTable("dbo.Telefones");
            DropTable("dbo.Enderecoes");
            DropTable("dbo.Comerciantes");
        }
    }
}
