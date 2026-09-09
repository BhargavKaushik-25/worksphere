CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE task (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    organization_id UUID NOT NULL,
    project_id UUID NOT NULL,
    assignee_id UUID,
    reporter_id UUID NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    task_status VARCHAR(20) NOT NULL DEFAULT 'TO_DO',
    priority VARCHAR(20) NOT NULL DEFAULT 'MEDIUM',
    progress SMALLINT NOT NULL DEFAULT 0,
    start_date DATE,
    due_date DATE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by UUID,
    deleted_at TIMESTAMP WITH TIME ZONE,
    deleted_by UUID,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT ck_task_status CHECK (task_status IN ('TO_DO', 'IN_PROGRESS', 'BLOCKED', 'COMPLETED')),
    CONSTRAINT ck_task_priority CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL')),
    CONSTRAINT ck_task_progress CHECK (progress BETWEEN 0 AND 100),
    CONSTRAINT ck_task_dates CHECK (due_date IS NULL OR start_date IS NULL OR due_date >= start_date),
    CONSTRAINT ck_task_completed_progress CHECK (task_status <> 'COMPLETED' OR progress = 100),
    CONSTRAINT ck_task_deleted CHECK ((is_deleted = FALSE AND deleted_at IS NULL) OR (is_deleted = TRUE AND deleted_at IS NOT NULL)),
    CONSTRAINT ck_task_version CHECK (version >= 0)
);

CREATE TABLE task_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    task_id UUID NOT NULL,
    changed_by UUID NOT NULL,
    action VARCHAR(50) NOT NULL,
    old_value JSONB,
    new_value JSONB,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by UUID,
    deleted_at TIMESTAMP WITH TIME ZONE,
    deleted_by UUID,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_task_history_task FOREIGN KEY (task_id) REFERENCES task (id),
    CONSTRAINT ck_task_history_action CHECK (action IN ('CREATED', 'UPDATED', 'ASSIGNED', 'STATUS_CHANGED', 'PRIORITY_CHANGED', 'COMPLETED')),
    CONSTRAINT ck_task_history_deleted CHECK ((is_deleted = FALSE AND deleted_at IS NULL) OR (is_deleted = TRUE AND deleted_at IS NOT NULL)),
    CONSTRAINT ck_task_history_version CHECK (version >= 0)
);

CREATE INDEX ix_task_organization_status ON task (organization_id, task_status) WHERE is_deleted = FALSE;
CREATE INDEX ix_task_project_status ON task (project_id, task_status) WHERE is_deleted = FALSE;
CREATE INDEX ix_task_assignee_status ON task (assignee_id, task_status) WHERE is_deleted = FALSE;
CREATE INDEX ix_task_priority ON task (priority) WHERE is_deleted = FALSE;
CREATE INDEX ix_task_due_date ON task (due_date) WHERE is_deleted = FALSE;
CREATE INDEX ix_task_history_task_time ON task_history (task_id, created_at DESC);
CREATE INDEX ix_task_history_changed_by ON task_history (changed_by, created_at DESC);
