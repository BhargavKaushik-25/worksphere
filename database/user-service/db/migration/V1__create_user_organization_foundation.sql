CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE organization (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_name VARCHAR(150) NOT NULL,
    organization_code VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by UUID,
    deleted_at TIMESTAMP WITH TIME ZONE,
    deleted_by UUID,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT ck_organization_deleted CHECK ((is_deleted = FALSE AND deleted_at IS NULL) OR (is_deleted = TRUE AND deleted_at IS NOT NULL)),
    CONSTRAINT ck_organization_version CHECK (version >= 0)
);

CREATE TABLE employee (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL,
    auth_user_id UUID NOT NULL,
    employee_code VARCHAR(50) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    account_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by UUID,
    deleted_at TIMESTAMP WITH TIME ZONE,
    deleted_by UUID,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_employee_organization FOREIGN KEY (organization_id) REFERENCES organization (id),
    CONSTRAINT ck_employee_status CHECK (account_status IN ('ACTIVE', 'INACTIVE')),
    CONSTRAINT ck_employee_deleted CHECK ((is_deleted = FALSE AND deleted_at IS NULL) OR (is_deleted = TRUE AND deleted_at IS NOT NULL)),
    CONSTRAINT ck_employee_version CHECK (version >= 0)
);

CREATE TABLE department (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL,
    department_name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by UUID,
    deleted_at TIMESTAMP WITH TIME ZONE,
    deleted_by UUID,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_department_organization FOREIGN KEY (organization_id) REFERENCES organization (id),
    CONSTRAINT ck_department_deleted CHECK ((is_deleted = FALSE AND deleted_at IS NULL) OR (is_deleted = TRUE AND deleted_at IS NOT NULL)),
    CONSTRAINT ck_department_version CHECK (version >= 0)
);

CREATE TABLE team (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL,
    department_id UUID NOT NULL,
    team_name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by UUID,
    deleted_at TIMESTAMP WITH TIME ZONE,
    deleted_by UUID,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_team_organization FOREIGN KEY (organization_id) REFERENCES organization (id),
    CONSTRAINT fk_team_department FOREIGN KEY (department_id) REFERENCES department (id),
    CONSTRAINT ck_team_deleted CHECK ((is_deleted = FALSE AND deleted_at IS NULL) OR (is_deleted = TRUE AND deleted_at IS NOT NULL)),
    CONSTRAINT ck_team_version CHECK (version >= 0)
);

CREATE TABLE employee_department (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id UUID NOT NULL,
    department_id UUID NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by UUID,
    deleted_at TIMESTAMP WITH TIME ZONE,
    deleted_by UUID,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_employee_department_employee FOREIGN KEY (employee_id) REFERENCES employee (id),
    CONSTRAINT fk_employee_department_department FOREIGN KEY (department_id) REFERENCES department (id),
    CONSTRAINT ck_employee_department_deleted CHECK ((is_deleted = FALSE AND deleted_at IS NULL) OR (is_deleted = TRUE AND deleted_at IS NOT NULL)),
    CONSTRAINT ck_employee_department_version CHECK (version >= 0)
);

CREATE TABLE employee_team (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id UUID NOT NULL,
    team_id UUID NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by UUID,
    deleted_at TIMESTAMP WITH TIME ZONE,
    deleted_by UUID,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_employee_team_employee FOREIGN KEY (employee_id) REFERENCES employee (id),
    CONSTRAINT fk_employee_team_team FOREIGN KEY (team_id) REFERENCES team (id),
    CONSTRAINT ck_employee_team_deleted CHECK ((is_deleted = FALSE AND deleted_at IS NULL) OR (is_deleted = TRUE AND deleted_at IS NOT NULL)),
    CONSTRAINT ck_employee_team_version CHECK (version >= 0)
);

CREATE UNIQUE INDEX ux_organization_code_active ON organization (LOWER(organization_code)) WHERE is_deleted = FALSE;
CREATE INDEX ix_organization_name_active ON organization (LOWER(organization_name)) WHERE is_deleted = FALSE;
CREATE UNIQUE INDEX ux_employee_auth_user_active ON employee (auth_user_id) WHERE is_deleted = FALSE;
CREATE UNIQUE INDEX ux_employee_code_org_active ON employee (organization_id, LOWER(employee_code)) WHERE is_deleted = FALSE;
CREATE UNIQUE INDEX ux_employee_email_org_active ON employee (organization_id, LOWER(email)) WHERE is_deleted = FALSE;
CREATE INDEX ix_employee_organization_status ON employee (organization_id, account_status) WHERE is_deleted = FALSE;
CREATE UNIQUE INDEX ux_department_name_org_active ON department (organization_id, LOWER(department_name)) WHERE is_deleted = FALSE;
CREATE INDEX ix_department_organization_active ON department (organization_id) WHERE is_deleted = FALSE;
CREATE UNIQUE INDEX ux_team_name_department_active ON team (department_id, LOWER(team_name)) WHERE is_deleted = FALSE;
CREATE INDEX ix_team_organization_active ON team (organization_id) WHERE is_deleted = FALSE;
CREATE INDEX ix_employee_department_employee ON employee_department (employee_id) WHERE is_deleted = FALSE;
CREATE INDEX ix_employee_department_department ON employee_department (department_id) WHERE is_deleted = FALSE;
CREATE UNIQUE INDEX ux_employee_department_active ON employee_department (employee_id, department_id) WHERE is_deleted = FALSE;
CREATE INDEX ix_employee_team_employee ON employee_team (employee_id) WHERE is_deleted = FALSE;
CREATE INDEX ix_employee_team_team ON employee_team (team_id) WHERE is_deleted = FALSE;
CREATE UNIQUE INDEX ux_employee_team_active ON employee_team (employee_id, team_id) WHERE is_deleted = FALSE;
