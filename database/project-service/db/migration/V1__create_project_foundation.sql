CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE project (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL,
    workspace_id UUID,
    project_name VARCHAR(150) NOT NULL,
    project_key VARCHAR(30) NOT NULL,
    description TEXT,
    project_status VARCHAR(20) NOT NULL DEFAULT 'PLANNED',
    project_manager_id UUID NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by UUID,
    deleted_at TIMESTAMP WITH TIME ZONE,
    deleted_by UUID,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT ck_project_status CHECK (project_status IN ('PLANNED', 'ACTIVE', 'COMPLETED', 'ARCHIVED')),
    CONSTRAINT ck_project_dates CHECK (end_date IS NULL OR end_date >= start_date),
    CONSTRAINT ck_project_deleted CHECK ((is_deleted = FALSE AND deleted_at IS NULL) OR (is_deleted = TRUE AND deleted_at IS NOT NULL)),
    CONSTRAINT ck_project_version CHECK (version >= 0)
);

CREATE TABLE project_member (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    project_id UUID NOT NULL,
    member_id UUID NOT NULL,
    project_role VARCHAR(30) NOT NULL DEFAULT 'MEMBER',
    joined_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by UUID,
    deleted_at TIMESTAMP WITH TIME ZONE,
    deleted_by UUID,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_project_member_project FOREIGN KEY (project_id) REFERENCES project (id),
    CONSTRAINT ck_project_member_role CHECK (project_role IN ('MANAGER', 'MEMBER')),
    CONSTRAINT ck_project_member_deleted CHECK ((is_deleted = FALSE AND deleted_at IS NULL) OR (is_deleted = TRUE AND deleted_at IS NOT NULL)),
    CONSTRAINT ck_project_member_version CHECK (version >= 0)
);

CREATE UNIQUE INDEX ux_project_key_org_active ON project (organization_id, LOWER(project_key)) WHERE is_deleted = FALSE;
CREATE INDEX ix_project_organization_status ON project (organization_id, project_status) WHERE is_deleted = FALSE;
CREATE INDEX ix_project_manager_active ON project (project_manager_id) WHERE is_deleted = FALSE;
CREATE INDEX ix_project_workspace_active ON project (workspace_id) WHERE is_deleted = FALSE;
CREATE INDEX ix_project_dates_active ON project (start_date, end_date) WHERE is_deleted = FALSE;
CREATE UNIQUE INDEX ux_project_member_active ON project_member (project_id, member_id) WHERE is_deleted = FALSE;
CREATE INDEX ix_project_member_member_active ON project_member (member_id) WHERE is_deleted = FALSE;
CREATE INDEX ix_project_member_project_role ON project_member (project_id, project_role) WHERE is_deleted = FALSE;
