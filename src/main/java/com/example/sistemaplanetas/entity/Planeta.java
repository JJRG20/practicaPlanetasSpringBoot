@Entity
@Table(name = "planeta")
@SQLDelete(sql = "UPDATE planeta SET deleted_at = NOW() WHERE id_planet = ?")
@Where(clause = "deleted_at IS NULL")
@Getter @Setter
public class Planeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlanet;

    @Column(nullable = false)
    private String name;

    private Double diameter;
    private Double weight;
    private Double sunDist;
    private Double time;

    @OneToMany(mappedBy = "planeta", cascade = CascadeType.ALL)
    private List<Luna> luna;
}
