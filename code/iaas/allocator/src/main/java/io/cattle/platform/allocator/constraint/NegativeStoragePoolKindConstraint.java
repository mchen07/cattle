package io.cattle.platform.allocator.constraint;

import io.cattle.platform.allocator.service.AllocationAttempt;
import io.cattle.platform.allocator.service.AllocationCandidate;
import io.cattle.platform.core.model.StoragePool;

import java.util.Map;
import java.util.Set;

public class NegativeStoragePoolKindConstraint implements Constraint {

    String kind;

    public NegativeStoragePoolKindConstraint(String kind) {
        super();
        this.kind = kind;
    }

    @Override
    public boolean matches(AllocationAttempt attempt, AllocationCandidate candidate) {
        for ( Map.Entry<Long,Set<Long>> entry : candidate.getPools().entrySet() ) {
            for ( Long id : entry.getValue() ) {
                StoragePool pool = candidate.loadResource(StoragePool.class, id);

                if ( kind.equals(pool.getKind()) ) {
                    return false;
                }
            }
        }

        return true;
    }


    @Override
    public String toString() {
        return String.format("storagePool.kind must not be %s", kind);
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

}
