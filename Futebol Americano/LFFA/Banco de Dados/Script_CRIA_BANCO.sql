USE [LIFFA]
GO
/****** Object:  Table [dbo].[t_jogador]    Script Date: 05/10/2016 20:38:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[t_jogador](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nome] [nvarchar](100) NULL,
	[foto] [nvarchar](100) NULL,
	[posicao] [nvarchar](100) NULL,
	[descricao] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[T_BOLAO_CAMPEONATO]    Script Date: 05/10/2016 20:38:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[T_BOLAO_CAMPEONATO](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nome] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[acessos]    Script Date: 05/10/2016 20:38:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[acessos](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[numero_acessos] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[times]    Script Date: 05/10/2016 20:38:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[times](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nome] [nvarchar](100) NULL,
	[escudo] [nvarchar](100) NULL,
	[presidente] [nvarchar](100) NULL,
	[descricao] [nvarchar](500) NULL,
	[telefone] [nvarchar](100) NULL,
	[data_fundacao] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[usuario]    Script Date: 05/10/2016 20:38:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[usuario](
	[id_usuario] [int] IDENTITY(1,1) NOT NULL,
	[nome_usuario] [nvarchar](100) NULL,
	[senha_usuario] [nvarchar](12) NULL,
	[email_usuario] [nvarchar](100) NULL,
	[tipo_usuario] [int] NULL,
	[token] [nchar](2000) NULL,
PRIMARY KEY CLUSTERED 
(
	[id_usuario] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Token]    Script Date: 05/10/2016 20:38:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Token](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[token] [nchar](1000) NOT NULL,
	[id_usuario] [int] NULL,
 CONSTRAINT [PK_Token] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[noticia]    Script Date: 05/10/2016 20:38:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[noticia](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[titulo] [nchar](100) NOT NULL,
	[noticia] [nchar](500) NOT NULL,
	[id_time] [int] NULL,
	[id_usuario] [int] NULL,
	[data_noticia] [datetime] NOT NULL,
 CONSTRAINT [PK_noticia] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[campeonato]    Script Date: 05/10/2016 20:38:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[campeonato](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[data_inicio] [datetime] NULL,
	[nome] [nvarchar](100) NULL,
	[id_bolao] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[artilharia]    Script Date: 05/10/2016 20:38:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[artilharia](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_jogador] [int] NOT NULL,
	[id_campeonato] [int] NOT NULL,
	[id_time] [int] NOT NULL,
	[numero_gols] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[classificacao]    Script Date: 05/10/2016 20:38:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[classificacao](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pontos] [int] NULL,
	[jogos] [int] NULL,
	[vitoria] [int] NULL,
	[derrota] [int] NULL,
	[empate] [int] NULL,
	[gol_pro] [int] NULL,
	[gol_contra] [int] NULL,
	[id_campeonato] [int] NULL,
	[id_time] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[partida]    Script Date: 05/10/2016 20:38:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[partida](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_time_mandante] [int] NULL,
	[id_time_visitante] [int] NULL,
	[id_campeonato] [int] NULL,
	[data_partida] [datetime] NULL,
	[local_partida] [nvarchar](100) NULL,
	[rodada] [nvarchar](100) NULL,
	[remarcada_partida] [bit] NULL,
	[gol_time_mandante] [int] NULL,
	[gol_time_visitante] [int] NULL,
	[pontos_computado] [int] NULL,
	[nova_data_partida] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[time_campeonato]    Script Date: 05/10/2016 20:38:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[time_campeonato](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[id_time] [int] NULL,
	[id_campeonato] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[t_jogo_online]    Script Date: 05/10/2016 20:38:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[t_jogo_online](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[comentario] [nchar](100) NOT NULL,
	[data_hora_comentario] [datetime] NOT NULL,
	[id_partida] [int] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[t_bolao]    Script Date: 05/10/2016 20:38:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[t_bolao](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[gol_time_mandante] [int] NULL,
	[gol_time_visitante] [int] NULL,
	[pontos_adquiridos] [int] NULL,
	[id_partida] [int] NULL,
	[id_usuario] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[fotos_videos]    Script Date: 05/10/2016 20:38:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[fotos_videos](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[caminho] [nvarchar](100) NULL,
	[derscricao] [nvarchar](100) NULL,
	[id_time] [int] NULL,
	[video] [nvarchar](100) NULL,
	[id_campeonato] [int] NULL,
	[id_partida] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  ForeignKey [FK_artilharia_campeonato]    Script Date: 05/10/2016 20:38:41 ******/
ALTER TABLE [dbo].[artilharia]  WITH CHECK ADD  CONSTRAINT [FK_artilharia_campeonato] FOREIGN KEY([id_campeonato])
REFERENCES [dbo].[campeonato] ([id])
GO
ALTER TABLE [dbo].[artilharia] CHECK CONSTRAINT [FK_artilharia_campeonato]
GO
/****** Object:  ForeignKey [FK_artilharia_t_jogador]    Script Date: 05/10/2016 20:38:41 ******/
ALTER TABLE [dbo].[artilharia]  WITH CHECK ADD  CONSTRAINT [FK_artilharia_t_jogador] FOREIGN KEY([id_jogador])
REFERENCES [dbo].[t_jogador] ([id])
GO
ALTER TABLE [dbo].[artilharia] CHECK CONSTRAINT [FK_artilharia_t_jogador]
GO
/****** Object:  ForeignKey [FK_artilharia_t_time]    Script Date: 05/10/2016 20:38:41 ******/
ALTER TABLE [dbo].[artilharia]  WITH CHECK ADD  CONSTRAINT [FK_artilharia_t_time] FOREIGN KEY([id_time])
REFERENCES [dbo].[times] ([id])
GO
ALTER TABLE [dbo].[artilharia] CHECK CONSTRAINT [FK_artilharia_t_time]
GO
/****** Object:  ForeignKey [FK__campeonat__id_bo__36B12243]    Script Date: 05/10/2016 20:38:41 ******/
ALTER TABLE [dbo].[campeonato]  WITH CHECK ADD FOREIGN KEY([id_bolao])
REFERENCES [dbo].[T_BOLAO_CAMPEONATO] ([id])
GO
/****** Object:  ForeignKey [FK__classific__id_ca__2D27B809]    Script Date: 05/10/2016 20:38:41 ******/
ALTER TABLE [dbo].[classificacao]  WITH CHECK ADD FOREIGN KEY([id_campeonato])
REFERENCES [dbo].[campeonato] ([id])
GO
/****** Object:  ForeignKey [FK__classific__id_ti__2E1BDC42]    Script Date: 05/10/2016 20:38:41 ******/
ALTER TABLE [dbo].[classificacao]  WITH CHECK ADD FOREIGN KEY([id_time])
REFERENCES [dbo].[times] ([id])
GO
/****** Object:  ForeignKey [FK__fotos_vid__id_ca__286302EC]    Script Date: 05/10/2016 20:38:41 ******/
ALTER TABLE [dbo].[fotos_videos]  WITH CHECK ADD FOREIGN KEY([id_campeonato])
REFERENCES [dbo].[campeonato] ([id])
GO
/****** Object:  ForeignKey [FK__fotos_vid__id_pa__29572725]    Script Date: 05/10/2016 20:38:41 ******/
ALTER TABLE [dbo].[fotos_videos]  WITH CHECK ADD FOREIGN KEY([id_partida])
REFERENCES [dbo].[partida] ([id])
GO
/****** Object:  ForeignKey [FK__fotos_vid__id_ti__276EDEB3]    Script Date: 05/10/2016 20:38:41 ******/
ALTER TABLE [dbo].[fotos_videos]  WITH CHECK ADD FOREIGN KEY([id_time])
REFERENCES [dbo].[times] ([id])
GO
/****** Object:  ForeignKey [FK_noticia_times]    Script Date: 05/10/2016 20:38:42 ******/
ALTER TABLE [dbo].[noticia]  WITH CHECK ADD  CONSTRAINT [FK_noticia_times] FOREIGN KEY([id_time])
REFERENCES [dbo].[times] ([id])
GO
ALTER TABLE [dbo].[noticia] CHECK CONSTRAINT [FK_noticia_times]
GO
/****** Object:  ForeignKey [FK_noticia_usuario]    Script Date: 05/10/2016 20:38:42 ******/
ALTER TABLE [dbo].[noticia]  WITH CHECK ADD  CONSTRAINT [FK_noticia_usuario] FOREIGN KEY([id_usuario])
REFERENCES [dbo].[usuario] ([id_usuario])
GO
ALTER TABLE [dbo].[noticia] CHECK CONSTRAINT [FK_noticia_usuario]
GO
/****** Object:  ForeignKey [FK__partida__id_camp__22AA2996]    Script Date: 05/10/2016 20:38:42 ******/
ALTER TABLE [dbo].[partida]  WITH CHECK ADD FOREIGN KEY([id_campeonato])
REFERENCES [dbo].[campeonato] ([id])
GO
/****** Object:  ForeignKey [FK__partida__id_time__20C1E124]    Script Date: 05/10/2016 20:38:42 ******/
ALTER TABLE [dbo].[partida]  WITH CHECK ADD FOREIGN KEY([id_time_mandante])
REFERENCES [dbo].[times] ([id])
GO
/****** Object:  ForeignKey [FK__partida__id_time__21B6055D]    Script Date: 05/10/2016 20:38:42 ******/
ALTER TABLE [dbo].[partida]  WITH CHECK ADD FOREIGN KEY([id_time_visitante])
REFERENCES [dbo].[times] ([id])
GO
/****** Object:  ForeignKey [FK__t_bolao__id_part__2A4B4B5E]    Script Date: 05/10/2016 20:38:42 ******/
ALTER TABLE [dbo].[t_bolao]  WITH CHECK ADD FOREIGN KEY([id_partida])
REFERENCES [dbo].[partida] ([id])
GO
/****** Object:  ForeignKey [FK__t_bolao__id_usua__0519C6AF]    Script Date: 05/10/2016 20:38:42 ******/
ALTER TABLE [dbo].[t_bolao]  WITH CHECK ADD FOREIGN KEY([id_usuario])
REFERENCES [dbo].[usuario] ([id_usuario])
GO
/****** Object:  ForeignKey [FK_t_jogo_online_partida]    Script Date: 05/10/2016 20:38:42 ******/
ALTER TABLE [dbo].[t_jogo_online]  WITH CHECK ADD  CONSTRAINT [FK_t_jogo_online_partida] FOREIGN KEY([id_partida])
REFERENCES [dbo].[partida] ([id])
GO
ALTER TABLE [dbo].[t_jogo_online] CHECK CONSTRAINT [FK_t_jogo_online_partida]
GO
/****** Object:  ForeignKey [FK__time_camp__id_ca__2C3393D0]    Script Date: 05/10/2016 20:38:42 ******/
ALTER TABLE [dbo].[time_campeonato]  WITH CHECK ADD FOREIGN KEY([id_campeonato])
REFERENCES [dbo].[campeonato] ([id])
GO
/****** Object:  ForeignKey [FK__time_camp__id_ti__2B3F6F97]    Script Date: 05/10/2016 20:38:42 ******/
ALTER TABLE [dbo].[time_campeonato]  WITH CHECK ADD FOREIGN KEY([id_time])
REFERENCES [dbo].[times] ([id])
GO
/****** Object:  ForeignKey [FK_Token_usuario]    Script Date: 05/10/2016 20:38:42 ******/
ALTER TABLE [dbo].[Token]  WITH CHECK ADD  CONSTRAINT [FK_Token_usuario] FOREIGN KEY([id])
REFERENCES [dbo].[usuario] ([id_usuario])
GO
ALTER TABLE [dbo].[Token] CHECK CONSTRAINT [FK_Token_usuario]
GO
