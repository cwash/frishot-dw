package com.example.helloworld.db
import com.example.helloworld.api.DartData
import com.example.helloworld.core.Dart
import com.google.common.base.Optional
import com.google.common.collect.ImmutableMap
import com.google.inject.Inject
import io.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory
/**
 * CBW: Write me.
 *
 * @author Chris Wash <a href="mailto:chris.wash@gmail.com">chris.wash@gmail.com</a>
 */
class DartDAO extends AbstractDAO<Dart> {

    @Inject
    public DartDAO(SessionFactory factory) {
        super(factory)
    }

    def mapDartToDartData = {
        def hitTotals = it.positionHitTotals ?: new HashMap<>()
        new DartData(it.dartId, it.handle, it.totalHits, it.totalShots, ImmutableMap.copyOf(hitTotals))
    }

    private Dart findByDartId(String id) {
        return uniqueResult(namedQuery("com.example.helloworld.core.Dart.findById").setString("id", id))
    }

    private Dart findByDartHandle(String handle) {
        return uniqueResult(namedQuery("com.example.helloworld.core.Dart.findByHandle").setString("handle", handle))
    }

    public Optional<DartData> findById(String id) {
        Dart dart = findByDartId(id)
        return Optional.fromNullable(dart == null ? null : mapDartToDartData(dart))
    }

    public Optional<DartData> findByHandle(String handle) {
        Dart dart = findByDartHandle(handle)
        return Optional.fromNullable(dart == null ? null : mapDartToDartData(dart))
    }

    public List<DartData> findAll() {
        List<Dart> darts = list(namedQuery("com.example.helloworld.core.Dart.findAll"))
        List<DartData> dartDataList = darts.collect mapDartToDartData

        return dartDataList
    }

    public DartData create(Dart dart) {
        Dart savedDart = persist(dart)
        return mapDartToDartData(savedDart)
    }

    void incrementTotalShots(String id) {
        Dart dart = findByDartId(id)
        dart.totalShots++
        persist(dart)
    }

    void incrementTotalHits(String id) {
        Dart dart = findByDartId(id)
        dart.totalHits++
        persist(dart)
    }

    void updatePositionalHitTotals(String id, Integer position) {
        Dart dart = findByDartId(id)
        Integer total = dart.positionHitTotals.get(position) ?: 0
        total++
        dart.positionHitTotals.put(position, total)
        persist(dart)
    }
}
