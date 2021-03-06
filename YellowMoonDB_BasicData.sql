USE [YellowMoonDB]
GO
SET IDENTITY_INSERT [dbo].[PaymentMethods] ON 

INSERT [dbo].[PaymentMethods] ([methodType], [methodName]) VALUES (0, N'COD')
INSERT [dbo].[PaymentMethods] ([methodType], [methodName]) VALUES (1, N'MOMO')
SET IDENTITY_INSERT [dbo].[PaymentMethods] OFF
SET IDENTITY_INSERT [dbo].[PaymentStatus] ON 

INSERT [dbo].[PaymentStatus] ([statusID], [statusName]) VALUES (0, N'Error')
INSERT [dbo].[PaymentStatus] ([statusID], [statusName]) VALUES (1, N'Pending')
INSERT [dbo].[PaymentStatus] ([statusID], [statusName]) VALUES (2, N'Success')
SET IDENTITY_INSERT [dbo].[PaymentStatus] OFF
SET IDENTITY_INSERT [dbo].[ProductStatus] ON 

INSERT [dbo].[ProductStatus] ([statusID], [statusName]) VALUES (1, N'ACTIVATE')
INSERT [dbo].[ProductStatus] ([statusID], [statusName]) VALUES (0, N'DEACTIVATED')
SET IDENTITY_INSERT [dbo].[ProductStatus] OFF
SET IDENTITY_INSERT [dbo].[UserRoles] ON 

INSERT [dbo].[UserRoles] ([roleID], [roleName]) VALUES (1, N'ADMIN')
INSERT [dbo].[UserRoles] ([roleID], [roleName]) VALUES (0, N'USER')
SET IDENTITY_INSERT [dbo].[UserRoles] OFF
