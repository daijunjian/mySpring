
CREATE TABLE Elec_Antenna(
	AntennaID VARCHAR(50) NOT NULL,
	JctID VARCHAR(50)  NULL,
	AntennaName VARCHAR(50)   NULL,
	TxZengyi VARCHAR(50)  NULL,
	SPBSWidth VARCHAR(50)  NULL,
	JiHuaFangShi VARCHAR(50)  NULL,
	TxChengshi VARCHAR(50)  NULL,
	WorkHZ VARCHAR(50)  NULL,
	COMMENT VARCHAR(500)  NULL,
	IsDelete VARCHAR(10)   NULL,
	CreateEmpID VARCHAR(50)  NULL,
	CreateDate DATETIME NULL,
	LastEmpID VARCHAR(50)  NULL,
	LastDate DATETIME NULL
) 



CREATE TABLE Elec_Device(
	DevID VARCHAR(50)  NOT NULL,
	DevPlanID VARCHAR(50)   NULL,
	JctID VARCHAR(50)   NULL,
	DevName VARCHAR(50)   NULL,
	DevType VARCHAR(10)   NULL,
	Trademark VARCHAR(50)  NULL,
	SpecType VARCHAR(50)  NULL,
	ProduceHome VARCHAR(50)  NULL,
	ProduceArea VARCHAR(50)  NULL,
	Useness VARCHAR(50)  NULL,
	Quality VARCHAR(10)  NULL,
	UseUnit VARCHAR(50)  NULL,
	DevExpense NUMERIC(20, 2) NULL,
	AdjustPeriod VARCHAR(50)  NULL,
	OverhaulPeriod VARCHAR(50)  NULL,
	Configure VARCHAR(100)  NULL,
	DevState VARCHAR(10)  NULL,
	RunDescribe VARCHAR(500)  NULL,
	COMMENT VARCHAR(500)  NULL,
	UseDate DATETIME NULL,
	IsDelete VARCHAR(10)   NULL,
	CreateEmpID VARCHAR(50)  NULL,
	CreateDate DATETIME NULL,
	LastEmpID VARCHAR(50)  NULL,
	LastDate DATETIME NULL,
	QUnit VARCHAR(10)  NULL,
	APUnit VARCHAR(10)  NULL,
	OPUnit VARCHAR(10)  NULL,
	APState VARCHAR(10)  NULL,
	OPState VARCHAR(10)  NULL
) 

CREATE TABLE Elec_Device_Plan(
	DevPlanID VARCHAR(50)  NOT NULL,
	JctID VARCHAR(50)   NULL,
	DevName VARCHAR(100)   NULL,
	DevType VARCHAR(10)   NULL,
	Trademark VARCHAR(50)  NULL,
	SpecType VARCHAR(50)  NULL,
	ProduceHome VARCHAR(50)  NULL,
	ProduceArea VARCHAR(50)  NULL,
	Useness VARCHAR(50)  NULL,
	Quality VARCHAR(10)  NULL,
	UseUnit VARCHAR(50)  NULL,
	DevExpense NUMERIC(20, 2) NULL,
	PlanDate DATETIME NULL,
	AdjustPeriod VARCHAR(50)  NULL,
	OverhaulPeriod VARCHAR(50)  NULL,
	Configure VARCHAR(50)  NULL,
	COMMENT VARCHAR(500)  NULL,
	PurchaseState VARCHAR(10)  NULL,
	IsDelete VARCHAR(10)  NULL,
	CreateEmpID VARCHAR(50)  NULL,
	CreateDate DATETIME NULL,
	LastEmpID VARCHAR(50)  NULL,
	LastDate DATETIME NULL,
	QUnit VARCHAR(10)  NULL,
	APUnit VARCHAR(10)  NULL,
	OPUnit VARCHAR(10)  NULL
) 

CREATE TABLE Elec_Engineering(
	EngineID VARCHAR(50)  NOT NULL,
	ProjID VARCHAR(50)   NULL,
	EngineName VARCHAR(100)   NULL,
	EngineState VARCHAR(10)   NULL,
	IsDelete VARCHAR(10)   NULL,
	CreateEmpID VARCHAR(50)  NULL,
	CreateDate DATETIME NULL,
	LastEmpID VARCHAR(50)  NULL,
	LastDate DATETIME NULL
) 

CREATE TABLE Elec_Engineering_Plan(
	EnginePlanID VARCHAR(50)  NOT NULL,
	ProjPlanID VARCHAR(50)   NULL,
	EngineName VARCHAR(100)   NULL,
	IsDelete VARCHAR(10)   NULL,
	CreateEmpID VARCHAR(50)  NULL,
	CreateDate DATETIME NULL,
	LastEmpID VARCHAR(50)  NULL,
	LastDate DATETIME NULL
) 
CREATE TABLE Elec_Engineering_Plan_History(
	SepId VARCHAR(50)  NOT NULL,
	EnginePlanID VARCHAR(50)   NULL,
	ProjPlanID VARCHAR(50)   NULL,
	EngineName VARCHAR(100)   NULL,
	IsDelete VARCHAR(10)   NULL,
	CreateEmpID VARCHAR(50)  NULL,
	CreateDate DATETIME NULL,
	LastEmpID VARCHAR(50)  NULL,
	LastDate DATETIME NULL,
	DeclareYear VARCHAR(50)  NULL
) 

CREATE TABLE Elec_ExportFields(
	BelongTo VARCHAR(10)  NOT NULL,
	ExpNameList VARCHAR(5000)  NULL,
	ExpFieldName VARCHAR(5000)  NULL,
	NoExpNameList VARCHAR(5000)  NULL,
	NoExpFieldName VARCHAR(5000)  NULL
)
CREATE TABLE Elec_FileUpload(
	ProjID VARCHAR(50)  NULL,
	BelongTo VARCHAR(50)  NULL,
	FileName VARCHAR(50)  NULL,
	FileURL VARCHAR(1000)  NULL,
	ProgressTime VARCHAR(20)  NULL,
	COMMENT VARCHAR(500)  NULL,
	IsDelete VARCHAR(10)  NULL,
	CreateEmpID VARCHAR(50)  NULL,
	CreateDate DATETIME NULL,
	SeqID INT NOT NULL
) 

CREATE TABLE Elec_JctBuild(
	BuildID VARCHAR(50)  NOT NULL,
	JctID VARCHAR(50)   NULL,
	BuildName VARCHAR(50)   NULL,
	BuildType VARCHAR(10)   NULL,
	BuildStartDate DATETIME NULL,
	DxDate DATETIME NULL,
	UseDate DATETIME NULL,
	BuildLayer VARCHAR(50)  NULL,
	BuildArea VARCHAR(50)  NULL,
	ExtendBuildDate DATETIME NULL,
	ExtendBuildArea VARCHAR(50)  NULL,
	BuildExpense NUMERIC(20, 2) NULL,
	COMMENT VARCHAR(500)  NULL,
	IsDelete VARCHAR(10)   NULL,
	CreateEmpID VARCHAR(50)  NULL,
	CreateDate DATETIME NULL,
	LastEmpID VARCHAR(50)  NULL,
	LastDate DATETIME NULL
 ) 
CREATE TABLE Elec_MaintainPlan(
	PlanID VARCHAR(50)  NOT NULL,
	JctID VARCHAR(50)   NULL,
	OccurDate DATETIME  NULL,
	MainContent VARCHAR(500)  NULL,
	COMMENT VARCHAR(500)  NULL
)

CREATE TABLE Elec_Overhaul_Record(
	DevID VARCHAR(50)   NULL,
	IsAdjust VARCHAR(10)   NULL,
	StartDate DATETIME  NULL,
	EndDate DATETIME  NULL,
	IsHaving VARCHAR(10)   NULL,
	Record VARCHAR(500)  NULL,
	COMMENT VARCHAR(500)  NULL,
	IsDelete VARCHAR(10)   NULL,
	CreateEmpID VARCHAR(50)  NULL,
	CreateDate DATETIME NULL,
	LastEmpID VARCHAR(50)  NULL,
	LastDate DATETIME NULL,
	SeqID INT NOT NULL
) 
CREATE TABLE Elec_Project(
	ProjID VARCHAR(50)  NOT NULL,
	JctID VARCHAR(50)   NULL,
	LayProjID VARCHAR(50)   NULL,
	ProjName VARCHAR(100)   NULL,
	ProjState VARCHAR(10)   NULL,
	ProjType VARCHAR(50)   NULL,
	PlanInvest NUMERIC(20, 2) NULL,
	MainContent VARCHAR(100)  NULL,
	BuildDate DATETIME NULL,
	IsYanShou VARCHAR(10)   NULL,
	Cycle VARCHAR(50)  NULL,
	COMMENT VARCHAR(500)  NULL,
	IsDelete VARCHAR(10)   NULL,
	CreateEmpID VARCHAR(50)  NULL,
	CreateDate DATETIME NULL,
	LastEmpID VARCHAR(50)  NULL,
	LastDate DATETIME NULL,
	YanShouDate DATETIME NULL,
	ProjSize VARCHAR(50)  NULL
) 

CREATE TABLE Elec_Project_Declare(
	SeqID INT NOT NULL,
	ProjPlanID VARCHAR(50)   NULL,
	DeclareYear VARCHAR(10)   NULL,
	DeclareState VARCHAR(10)   NULL
)
CREATE TABLE Elec_Project_Layout(
	LayProjID VARCHAR(50)  NOT NULL,
	ProjName VARCHAR(100)   NULL,
	SeqID INT  NULL,
	ProjGrade INT  NULL,
	ParentProjID VARCHAR(50)   NULL,
	Property VARCHAR(10)   NULL,
	Size VARCHAR(50)  NULL,
	Adress VARCHAR(50)  NULL,
	StartTime DATETIME NULL,
	EndTime DATETIME NULL,
	PlanStartTime DATETIME NULL,
	PlanInvest NUMERIC(20, 2) NULL,
	MainContent VARCHAR(500)  NULL,
	COMMENT VARCHAR(500)  NULL,
	IsDelete VARCHAR(10)   NULL,
	CreateEmpID VARCHAR(50)  NULL,
	CreateDate DATETIME NULL,
	LastEmpID VARCHAR(50)  NULL,
	LastDate DATETIME NULL,
	VarSeqId VARCHAR(50)  NULL
) 

CREATE TABLE Elec_Project_Plan(
	ProjPlanID VARCHAR(50)  NOT NULL,
	JctID VARCHAR(50)   NULL,
	LayProjID VARCHAR(50)   NULL,
	ProjName VARCHAR(100)   NULL,
	ProjState VARCHAR(10)   NULL,
	ProjType VARCHAR(10)   NULL,
	DeclareDate DATETIME NULL,
	PlanInvest NUMERIC(20, 2) NULL,
	KeYan VARCHAR(10)   NULL,
	ChuShe VARCHAR(10)   NULL,
	KeYanInvest NUMERIC(20, 2) NULL,
	ChuSheInvest NUMERIC(20, 2) NULL,
	IsDeclare VARCHAR(10)   NULL,
	IsApprove VARCHAR(10)   NULL,
	Gist VARCHAR(100)  NULL,
	MainContent VARCHAR(500)  NULL,
	COMMENT VARCHAR(500)  NULL,
	IsDelete VARCHAR(10)   NULL,
	CreateEmpID VARCHAR(50)  NULL,
	CreateDate DATETIME NULL,
	LastEmpID VARCHAR(50)  NULL,
	LastDate DATETIME NULL,
	ProjSize VARCHAR(50)  NULL
)

 
CREATE TABLE Elec_Station(
	StationID VARCHAR(50)  NOT NULL,
	JctID VARCHAR(50)   NULL,
	StationCode VARCHAR(50)   NULL,
	StationName VARCHAR(50)   NULL,
	JCFrequency VARCHAR(100)  NULL,
	ProduceHome VARCHAR(50)  NULL,
	ContactType VARCHAR(50)  NULL,
	UseStartDate DATETIME NULL,
	COMMENT VARCHAR(500)  NULL,
	IsDelete VARCHAR(10)   NULL,
	CreateEmpID VARCHAR(50)  NULL,
	CreateDate DATETIME NULL,
	LastEmpID VARCHAR(50)  NULL,
	LastDate DATETIME NULL,
	StationType VARCHAR(50)   NULL,
	AttributionGround VARCHAR(50)  NULL,
	period VARCHAR(50)  NULL
)
CREATE TABLE Elec_StationBug(
	BugID INT  NOT NULL,
	StationID VARCHAR(50)   NULL,
	JctID VARCHAR(50)   NULL,
	SbMonth VARCHAR(50)   NULL,
	BugType VARCHAR(10)   NULL,
	OccurDate VARCHAR(50)   NULL,
	LauncherName VARCHAR(50)  NULL,
	BugDescribe VARCHAR(500)  NULL,
	ResolveDate VARCHAR(50)  NULL,
	ResolveMethod VARCHAR(500)  NULL,
	BugReason VARCHAR(500)  NULL,
	COMMENT VARCHAR(500)  NULL,
	IsDelete VARCHAR(10)   NULL,
	CreateEmpID VARCHAR(50)  NULL,
	CreateDate DATETIME NULL,
	LastEmpID VARCHAR(50)  NULL,
	LastDate DATETIME NULL
)
CREATE TABLE Elec_SubEngineering(
	SubEngineID VARCHAR(50)  NOT NULL,
	ProjID VARCHAR(50)   NULL,
	EngineID VARCHAR(50)   NULL,
	SubEngineName VARCHAR(100)   NULL,
	SubEngineState VARCHAR(10)   NULL,
	UnitPrice NUMERIC(20, 2) NULL,
	Quality INT NULL,
	PlanInvest NUMERIC(20, 2) NULL,
	InvestMonth VARCHAR(50)  NULL,
	ActualInvest NUMERIC(20, 2) NULL,
	TiaoGaiInvest NUMERIC(20, 2) NULL,
	COMMENT VARCHAR(500)  NULL,
	IsDelete VARCHAR(10)   NULL,
	CreateEmpID VARCHAR(50)  NULL,
	CreateDate DATETIME NULL,
	LastEmpID VARCHAR(50)  NULL,
	LastDate DATETIME NULL
)
CREATE TABLE Elec_SubEngineering_Plan(
	SubEngineID VARCHAR(50)  NOT NULL,
	ProjPlanID VARCHAR(50)   NULL,
	EnginePlanID VARCHAR(50)   NULL,
	SubEngineName VARCHAR(100)   NULL,
	UnitPrice NUMERIC(20, 2) NULL,
	Quality INT NULL,
	PlanInvest NUMERIC(20, 2) NULL,
	COMMENT VARCHAR(500)  NULL,
	IsDelete VARCHAR(10)   NULL,
	CreateEmpID VARCHAR(50)  NULL,
	CreateDate DATETIME NULL,
	LastEmpID VARCHAR(50)  NULL,
	LastDate DATETIME NULL
) 
CREATE TABLE Elec_SubEngineering_Plan_History(
	SsepId VARCHAR(50)  NOT NULL,
	SubEngineID VARCHAR(50)   NULL,
	ProjPlanID VARCHAR(50)   NULL,
	EnginePlanID VARCHAR(50)   NULL,
	SubEngineName VARCHAR(100)   NULL,
	UnitPrice NUMERIC(20, 2) NULL,
	Quality INT NULL,
	PlanInvest NUMERIC(20, 2) NULL,
	COMMENT VARCHAR(500)  NULL,
	IsDelete VARCHAR(10)   NULL,
	CreateEmpID VARCHAR(50)  NULL,
	CreateDate DATETIME NULL,
	LastEmpID VARCHAR(50)  NULL,
	LastDate DATETIME NULL,
	DeclareYear VARCHAR(50)   NULL
) 
#代办事宜
CREATE TABLE Elec_CommonMsg(
	ComID VARCHAR(50)  NOT NULL,    #主键ID
	StationRun VARCHAR(5000)  NULL, #站点运行情况
	DevRun VARCHAR(5000)  NULL,    #设备运行情况
	CreateDate DATETIME NULL        #创建日期
	#CreateEmpCode VARCHAR(50)  NULL,#创建人
	
) 

#数据字典
CREATE TABLE Elec_SystemDDL(
	SeqID INT NOT NULL,          #主键ID(自增长)
	Keyword VARCHAR(20)   NULL,  #数据类型
	DdlCode INT  NULL,           #数据项的code
	DdlName VARCHAR(50)  NULL    #数据项的value
) 
#用户表
CREATE TABLE Elec_User(
	UserID VARCHAR(50)  NOT NULL, #主键ID
	JctID VARCHAR(50)   NULL,     #所属单位code
	UserName VARCHAR(50)   NULL,  #用户姓名
	LogonName VARCHAR(50)   NULL, #登录名
	LogonPwd VARCHAR(50)  NULL,   #密码#
	SexID VARCHAR(10)  NULL,      #性别
	Birthday DATETIME NULL,       #出生日期
	Address VARCHAR(100)  NULL,    #联系地址
	ContactTel VARCHAR(50)  NULL, #联系电话 
	Email VARCHAR(50)  NULL,      #电子邮箱
	Mobile VARCHAR(50)  NULL,     #手机
	IsDuty VARCHAR(10)  NULL,     #是否在职
	OnDutyDate DATETIME NULL,     #入职时间
	OffDutyDate DATETIME NULL,    #离职时间
	remark VARCHAR(500)  NULL   #备注
	#IsDelete VARCHAR(10)   NULL,  #是否删除
	#CreateEmpID VARCHAR(50)  NULL,#创建人ID
	#CreateDate DATETIME NULL,     #创建时间
	#LastEmpID VARCHAR(50)  NULL,  #修改人ID
	#LastDate DATETIME NULL        #修改时间
) 
#用户角色信息表
CREATE TABLE Elec_User_Role(
	SeqID INT NOT NULL,               #主键ID
	UserID VARCHAR(50)   NULL,        #用户ID
	RoleID VARCHAR(50)   NULL,        #角色ID
	remark VARCHAR(500)  NULL      #备注
	#CreateEmpCode VARCHAR(50)  NULL,  #创建人
	#CreateDate DATETIME NULL          #创建时间
) 
#角色权限信息表
CREATE TABLE Elec_Role_Popedom(
	RoleID VARCHAR(50)  NOT NULL,      #主键ID
	Popedomcode VARCHAR(50)   NULL,    #配置web文件中权限的编码code的字符串连接
	remark VARCHAR(500)  NULL       #备注
	#IsDelete VARCHAR(10)   NULL,       #是否删除
	#CreateEmpCode VARCHAR(50)  NULL,   #创建人
	#CreateDate DATETIME NULL           #创建时间
)


#日志表
CREATE TABLE Elec_Log(
	LogID varchar(50) not null, #主键ID
	IpAddress varchar(50),			#IP地址
	OpeName varchar(50),				#操作人
	OpeTime DATETIME,						#操作时间
	Details varchar(500)        #操作明细
)

#测试表
CREATE TABLE Elec_Text(
	textID varchar(50) not null,
	textName varchar(50),
	textDate datetime,
	textRemark varchar(500)
)

-----------------------------------------------------------------工作流表


#申请模板表
create table Elec_Application_Template (
	id long not null, #主键ID
	name varchar(500),#名称
	processDefinitionKey varchar(500),#流程定义的key
	path varchar(5000)#上传的模板文件的存储位置
)

#申请模板与申请信息是1对多的关系
#申请信息表
create table Elec_Application (
	applicationID long not null, #主键ID
	applicationTemplateID varchar(50), #申请模板表的主键
	title varchar(100),#上传的文件标题
	path varchar(5000),#上传的文件的存储路径
	applicationUserID varchar(50),#申请人ID
	applicationLogonName varchar(50),#申请人登录名
	applicationUserName varchar(50),#申请人姓名
	applyTime TIMESTAMP,#申请日期
	status varchar(10) #当前审批状态
)

#申请信息与审批信息的关系是1对多关系
#审批信息表
create table Elec_ApproveInfo (
	approveID long not null, #主键ID
	applicationID varchar(50),#申请信息表的主键
	comment varchar(5000),#审批意见
	approval boolean, #审批结果，是否通过（同意或者不同意）
	approverUserID varchar(50),#审批人ID
	approverUserName varchar(50),#审批人姓名
	approveTime TIMESTAMP #审批日期
)






-----------------------------------------------------------------工作流表

#申请模板表
create table Elec_Application_Template (
	id long not null, #主键ID
	name varchar(500),#名称
	processDefinitionKey varchar(500),#流程定义的key
	path varchar(5000)#上传的模板文件的存储位置
)

#申请模板与申请信息是1对多的关系
#申请信息表
create table Elec_Application (
	id long not null, #主键ID
	applicationTemplateID long, #申请模板表的主键
	title varchar(100),#上传的文件标题
	path varchar(5000),#上传的文件的存储路径
	applicationUserID varchar(50),#申请人ID
	applicationLogonName varchar(50),#申请人登录名
	applicationName varchar(50),#申请人姓名
	applyTime TIMESTAMP,#申请日期
	status varchar(10) #当前审批状态
)

#申请信息与审批信息的关系是1对多关系
#审批信息表
create table Elec_ApproveInfo (
	id long not null, #主键ID
	applicationID long,#申请信息表的主键
	comment varchar(5000),#审批意见
	approval boolean, #审批结果，是否通过（同意或者不同意）
	approverUserID varchar(50),#审批人ID
	approverName varchar(50),#审批人姓名
	approveTime TIMESTAMP #审批日期
)

