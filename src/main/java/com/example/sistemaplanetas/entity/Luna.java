@Entity
@Table(name = "luna")
@SQLDelete(sql = "UPDATE luna SET deleted_at = NOW() WHERE id_luna = ?")
@Where(clause = "deleted_at IS NULL")
@Getter @Setter
public class Luna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLuna;

    @Column(nullable = false)
    private String name;

    private Double diameter;
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "id_planet")
    private Planeta planeta;
}
